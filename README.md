# TaskFlow

**Chapter 16 — CI/CD, release, and capstone**

Production-style Android app for **Android Advanced Techniques** (16 chapters, 48 hours). This branch is the course capstone: one app, not sixteen toy projects.

`versionName` **1.1.0** · `versionCode` **2**

## How to run

1. Open the folder in Android Studio.
2. Sync Gradle.
3. Run a flavor: `stagingDebug`, `uatDebug`, or `prodDebug`.

Classroom login and catalog traffic are served by `ClassroomMockInterceptor`. No live backend is required.

## Release

| Artifact | Command | Use |
|---|---|---|
| Staging debug APK | `./gradlew assembleStagingDebug` | Class demo, CI |
| Staging release AAB | `./gradlew bundleStagingRelease` | Internal testers if a development keystore exists |
| Prod release AAB | `./gradlew bundleProdRelease` | Play upload only, production keystore |

APK is for sideload. AAB is what Play wants. Staging AABs never go to the production listing.

Signing stays in `keystore.properties` plus `development_keystore` / `production_keystore` (see `app/keystore/README.md`). Those files stay out of git.

After a release minify build, keep `app/build/outputs/mapping/<flavor>Release/mapping.txt` if you later attach Crashlytics.

Bump `versionCode` on every store or Firebase App Distribution upload. Humans read `versionName`; Play compares the integer.

## CI

`.github/workflows/ci.yml` runs on push and pull request:

1. `./gradlew test`
2. `./gradlew assembleStagingDebug`

A green check that skips tests is not a pass. Do not put keystore passwords in YAML.

## Capstone list

| Area | In this repo |
|---|---|
| Core | Kotlin, Compose, MVVM, Clean packages, Coroutines, Flow, Hilt |
| Data | Retrofit, OkHttp, Room, DataStore, repositories, use cases |
| UX | Auth, REST profile, offline products, paging, registration form, `Resource` states |
| OS | WorkManager product sync + notification |
| Quality | Unit tests, debug/release, flavors, EncryptedSharedPreferences, R8, GitHub Actions |

Submit: this repo, PR history, AAB or APK, this README, the diagram below, API notes, `./gradlew test`, and a 5-minute talk.

## Architecture

```text
UI (Compose + Navigation)
        │
   ViewModel  ── Flow / StateFlow
        │
   Use cases
        │
   Repository
   ├── Room (notes, products, chat)
   ├── DataStore (theme)
   ├── EncryptedSharedPreferences (token)
   └── Retrofit + OkHttp (mocked classroom API)
        │
   WorkManager (product sync)
```

## API notes

| Flavor | `API_BASE_URL` |
|---|---|
| staging | `https://staging-api.taskflow.local/v1/` |
| uat | `https://uat-api.taskflow.local/v1/` |
| prod | `https://api.taskflow.app/v1/` |

`ClassroomMockInterceptor` answers login, profile, and product list after `AuthInterceptor` attaches `Bearer`. Swap the interceptor for a real host when a classroom server exists.

`firestore.rules` is the Chapter 14 cloud-rules stand-in. Chat messages persist in Room until a Firebase project is wired.

## Course branches

| Branch | Chapter |
|---|---|
| `dev/chapter7` | Room notes + DataStore theme |
| `dev/chapter8` | Retrofit profile + token interceptor |
| `dev/chapter9` | Offline-first paging products |
| `dev/chapter10` | Compose profile cards |
| `dev/chapter11` | Registration form state |
| `dev/chapter12` | Navigation Compose |
| `dev/chapter13` | WorkManager sync |
| `dev/chapter14` | Firebase-ready chat + rules |
| `dev/chapter15` | Tests + security + R8 |
| `dev/chapter16` | CI + version 1.1.0 + this README |
