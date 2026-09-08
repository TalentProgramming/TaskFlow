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
│   ├── network      ← debug vs release API URLs
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

## Build environments

| Build type | Environment | API URL |
|---|---|---|
| `debug` | development | `https://dev-api.taskflow.local/v1/` |
| `release` | production | `https://api.taskflow.app/v1/` |

Switch variants in Android Studio with **Build > Select Build Variant**.

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
