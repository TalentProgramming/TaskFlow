# TaskFlow

Production-style Android project for **Android Advanced Techniques**.

**Chapter 2 — Advanced Kotlin login module**

This branch extends the Chapter 1 foundation with a Kotlin-only login-state simulator. There is still no Retrofit, Room, Hilt, or coroutines.

## Demo credentials

| Field | Value |
|---|---|
| Email | `student@example.com` |
| Password | `123456` |

Anything else returns `Resource.Error("Invalid credentials")`. Invalid form input never calls the fake login.

## Where Chapter 2 Kotlin is used

| Feature | File |
|---|---|
| `Resource<out T>` — Loading / Success / Error / Empty | `core/common/Resource.kt` |
| `User` data class | `feature/auth/domain/User.kt` |
| `String.isValidEmail()` and `String.isStrongPassword()` | `utils/ValidationExtensions.kt` |
| `let` for email/password pairing | `LoginViewModel.onLoginClick()` |
| `also` when a successful user is created | `FakeAuthRepository.login()` |
| `run` to build the welcome text | `LoginScreen` success branch |
| Exhaustive `when` for UI state | `LoginStatePanel` |
| No `!!` | Project-wide |

Loading is simulated with `Handler.postDelayed` so the Loading screenshot is possible without coroutines. Chapter 3 replaces that delay with `viewModelScope` and `delay()`.

## Carried forward from Chapter 1

Product flavors and release signing stay in this branch.

| Flavor | Application ID | API URL | Release keystore |
|---|---|---|---|
| `staging` | `com.tp.taskflow.staging` | `https://staging-api.taskflow.local/v1/` | `development_keystore` |
| `uat` | `com.tp.taskflow.uat` | `https://uat-api.taskflow.local/v1/` | `development_keystore` |
| `prod` | `com.tp.taskflow` | `https://api.taskflow.app/v1/` | `production_keystore` |

Copy `keystore.properties.example` to `keystore.properties`. Do not commit the keystores or that file.

## How to run

1. Open this folder in Android Studio.
2. Sync Gradle.
3. Run the `app` configuration.
4. Capture Idle, Loading, Success, and Error.

## Not in this chapter

- Retrofit / real API
- Coroutines / Flow
- Room
- Hilt
- Firebase
