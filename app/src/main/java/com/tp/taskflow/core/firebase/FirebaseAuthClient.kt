package com.tp.taskflow.core.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthClient @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val crash: CrashReporter
) {
    val uid: String? get() = auth.currentUser?.uid

    suspend fun signInOrCreate(email: String, password: String, name: String) {
        val user = try {
            auth.signInWithEmailAndPassword(email, password).await().user
        } catch (signInError: Exception) {
            try {
                auth.createUserWithEmailAndPassword(email, password).await().user
            } catch (_: Exception) {
                throw signInError
            }
        } ?: error("Firebase Auth returned no user")
        persistProfile(user, name, email)
    }

    suspend fun signOut() {
        auth.signOut()
        crash.setUserId("")
    }

    private suspend fun persistProfile(user: FirebaseUser, name: String, email: String) {
        crash.setUserId(user.uid)
        firestore.collection("users").document(user.uid).set(
            mapOf(
                "name" to name,
                "email" to email,
                "updatedAt" to System.currentTimeMillis()
            ),
            SetOptions.merge()
        ).await()
    }
}
