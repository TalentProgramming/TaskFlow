package com.tp.taskflow.core.network

import com.tp.taskflow.BuildConfig

/**
 * Environment values come from product flavors (`staging` / `uat` / `prod`).
 * Build type (`debug` / `release`) only controls how the app is packaged.
 */
object ApiConfig {
    val baseUrl: String = BuildConfig.API_BASE_URL
    val environmentName: String = BuildConfig.ENV_NAME
    val flavor: String = BuildConfig.FLAVOR
    val buildType: String = BuildConfig.BUILD_TYPE
    val isDebugBuild: Boolean = BuildConfig.DEBUG
    val versionName: String = BuildConfig.VERSION_NAME
    val versionCode: Int = BuildConfig.VERSION_CODE
}
