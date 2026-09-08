package com.tp.taskflow.utils

fun String.isValidEmail(): Boolean {
    val trimmed = trim()
    return trimmed.contains("@") && trimmed.contains(".") && trimmed.length >= 5
}

fun String.isStrongPassword(): Boolean = length >= 6
