package com.tp.taskflow.feature.profile.data

import com.tp.taskflow.feature.profile.domain.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

import javax.inject.Inject

class FakeProfileDataSource @Inject constructor() : ProfileDataSource {
    suspend fun getProfile(): Profile = withContext(Dispatchers.IO) {
        delay(800)
        Profile(
            id = "u-1",
            name = "Aung Ko",
            email = "student@taskflow.app"
        )
    }
}
