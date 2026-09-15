package com.tp.taskflow.feature.auth.domain

import com.tp.taskflow.utils.isStrongPassword
import com.tp.taskflow.utils.isValidEmail

data class RegisterForm(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirm: String = "",
    val phone: String = "",
    val errors: Map<String, String> = emptyMap()
) {
    fun validated(): RegisterForm {
        val next = buildMap {
            if (name.trim().length < 2) put("name", "Enter your name")
            if (!email.isValidEmail()) put("email", "Invalid email")
            if (!password.isStrongPassword()) put("password", "At least 6 characters")
            if (confirm != password) put("confirm", "Passwords do not match")
            if (phone.filter { it.isDigit() }.length < 8) put("phone", "Enter a phone number")
        }
        return copy(errors = next)
    }
}
