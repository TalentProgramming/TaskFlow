package com.tp.taskflow.core.firebase

import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CrashReporter @Inject constructor(
    private val crashlytics: FirebaseCrashlytics
) {
    fun setUserId(uid: String) {
        crashlytics.setUserId(uid)
    }

    fun record(error: Throwable) {
        crashlytics.recordException(error)
    }

    fun testCrash() {
        crashlytics.log("classroom test crash")
        error("TaskFlow classroom Crashlytics test")
    }
}
