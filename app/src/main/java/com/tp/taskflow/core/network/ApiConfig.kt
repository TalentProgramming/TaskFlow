package com.tp.taskflow.core.network

import com.tp.taskflow.BuildConfig

/**
 * Environment values come from debug / release build types.
 * Real Retrofit clients arrive in Chapter 8.
 */
object ApiConfig {
    val baseUrl: String = BuildConfig.API_BASE_URL
    val environmentName: String = BuildConfig.ENV_NAME
    val isDebugBuild: Boolean = BuildConfig.DEBUG
}
