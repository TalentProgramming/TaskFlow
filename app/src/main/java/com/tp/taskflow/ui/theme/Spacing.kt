package com.tp.taskflow.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val xs: Dp = 8.dp,
    val md: Dp = 16.dp,
    val lg: Dp = 24.dp
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }
