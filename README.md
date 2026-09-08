# TaskFlow

Production-style Android starter for **Android Advanced Techniques**.

**Chapter 1 — Modern Android Development Architecture**

The app runs and shows `TaskFlow • Project Setup Complete`. Real login, networking, Room, Hilt, and coroutines are intentionally not implemented yet.

## Course progress

| Branch | Status |
|---|---|
| `main` | Chapter 1 foundation |
| `develop` | Integration branch |
| `feature/project-setup` | Chapter 1 homework branch |
| `dev/chapter1` | Same snapshot as Chapter 1 |
| `dev/chapter2` | Advanced Kotlin login simulator |
| `dev/chapter3` | Parallel coroutine dashboard |

## Architecture

Feature-based packages, with data / domain / presentation inside each feature.

```text
com.aal.taskflow
├── core
│   ├── common
│   ├── network      ← flavor API URLs (staging / uat / prod)
│   └── ui           ← setup-complete screen
├── feature
│   ├── auth         ← data / domain / presentation
│   ├── task         ← data / domain / presentation
│   └── profile      ← data / domain / presentation
├── di               ← empty until Chapter 6 (Hilt)
└── utils
```

```text
UI
 ↓
ViewModel      (later chapters)
 ↓
Use Case       (later chapters)
 ↓
Repository     (later chapters)
 ↓
Remote / Local (later chapters)
```

## Product flavors and build types

API URLs belong to **product flavors**. **Build types** only decide whether the package is debug or release.

| Flavor | Environment | Application ID | API URL |
|---|---|---|---|
| `staging` | staging | `com.tp.taskflow.staging` | `https://staging-api.taskflow.local/v1/` |
| `uat` | uat | `com.tp.taskflow.uat` | `https://uat-api.taskflow.local/v1/` |
| `prod` | production | `com.tp.taskflow` | `https://api.taskflow.app/v1/` |

Combined variants in **Build > Select Build Variant**:

- `stagingDebug` / `stagingRelease`
- `uatDebug` / `uatRelease`
- `prodDebug` / `prodRelease`

`staging` and `uat` can sit on the same device as `prod` because they use `applicationIdSuffix`.

## Release signing

Debug variants use the Android debug key. Release variants use the project keystores:

| Variant | Keystore |
|---|---|
| `stagingRelease` | `development_keystore` |
| `uatRelease` | `development_keystore` |
| `prodRelease` | `production_keystore` |

Copy `keystore.properties.example` to `keystore.properties` and fill in the passwords. Do not commit the keystore files or `keystore.properties`.

Dependencies are managed through `gradle/libs.versions.toml`.

## How to run

1. Open this folder in Android Studio.
2. Sync Gradle.
3. Run the `app` configuration.
4. Confirm the setup-complete screen and the current API URL.

## Not in this chapter

- Real login
- Retrofit / OkHttp
- Room
- Hilt
- Coroutines / Flow
- Notifications
