package com.tp.taskflow

import com.tp.taskflow.feature.auth.data.FakeAuthRepository
import com.tp.taskflow.core.common.Resource
import com.tp.taskflow.utils.isStrongPassword
import com.tp.taskflow.utils.isValidEmail
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidationExtensionsTest {

    @Test
    fun validEmail_acceptsDemoAccount() {
        assertTrue("student@example.com".isValidEmail())
    }

    @Test
    fun validEmail_rejectsMissingAt() {
        assertFalse("student.example.com".isValidEmail())
    }

    @Test
    fun strongPassword_requiresSixCharacters() {
        assertTrue("123456".isStrongPassword())
        assertFalse("12345".isStrongPassword())
    }

    @Test
    fun fakeLogin_returnsSuccessForDemoCredentials() {
        val result = FakeAuthRepository().login(
            FakeAuthRepository.DEMO_EMAIL,
            FakeAuthRepository.DEMO_PASSWORD
        )
        assertTrue(result is Resource.Success)
    }
}
