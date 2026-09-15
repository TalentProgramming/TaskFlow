package com.tp.taskflow

import com.tp.taskflow.feature.auth.domain.RegisterForm
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RegisterFormTest {

    @Test
    fun blankName_isError() {
        val errors = RegisterForm(name = " ", email = "a@b.com", password = "123456", confirm = "123456", phone = "0912345678")
            .validated().errors
        assertEquals("Enter your name", errors["name"])
    }

    @Test
    fun confirmMismatch_isError() {
        val errors = RegisterForm(name = "Aung", email = "a@b.com", password = "123456", confirm = "654321", phone = "0912345678")
            .validated().errors
        assertEquals("Passwords do not match", errors["confirm"])
    }

    @Test
    fun validForm_hasNoErrors() {
        val errors = RegisterForm(name = "Aung", email = "student@example.com", password = "123456", confirm = "123456", phone = "0912345678")
            .validated().errors
        assertTrue(errors.isEmpty())
    }
}
