# TaskFlow

Production-style Android project for **Android Advanced Techniques**.

**Chapter 3 — Kotlin Coroutines parallel dashboard**

After the Chapter 2 login simulation, the home dashboard loads profile, posts, and notifications together with `async` / `await`.

## How to demo

1. Run the `app` configuration.
2. Sign in with `student@example.com` / `123456`.
3. Tap **Open dashboard**.
4. First load fails on purpose (`Posts service unavailable`) so you can screenshot **Error** and tap **Retry**.
5. Retry loads all three sources in parallel and shows **Success**.

Watch Logcat tag `TaskFlow` for parallel timing. Sequential time would be about 800 + 900 + 1100 = 2800 ms.

## Carried forward from Chapter 1

Product flavors and release signing stay in this branch.

| Flavor | Application ID | API URL | Release keystore |
|---|---|---|---|
| `staging` | `com.tp.taskflow.staging` | `https://staging-api.taskflow.local/v1/` | `development_keystore` |
| `uat` | `com.tp.taskflow.uat` | `https://uat-api.taskflow.local/v1/` | `development_keystore` |
| `prod` | `com.tp.taskflow` | `https://api.taskflow.app/v1/` | `production_keystore` |

Copy `keystore.properties.example` to `keystore.properties`. Do not commit the keystores or that file.

## Coroutine design

| Piece | Choice |
|---|---|
| Scope | `viewModelScope` in `DashboardViewModel` — no data coroutine is started from the Activity |
| Start work | `launch` updates `Resource` state |
| Join values | `async` / `await()` inside `coroutineScope` |
| Threads | `withContext(Dispatchers.IO)` in fake data sources |
| Delay | `delay()` — simulated APIs only |
| Failure | `coroutineScope` is fail-fast; `CancellationException` is rethrown |
| Retry | Same `refresh()` path; safe to tap more than once |

Default homework path: `coroutineScope` + Retry. Partial success with `supervisorScope` is extra credit and is not required here.

## Key files

```text
feature/home/domain/LoadDashboardUseCase.kt
feature/home/presentation/DashboardViewModel.kt
feature/home/presentation/DashboardScreen.kt
feature/profile/data/FakeProfileDataSource.kt
feature/task/data/FakeTaskDataSource.kt
feature/home/data/FakeNotificationDataSource.kt
core/common/Resource.kt
```

## Not in this chapter

- Retrofit / OkHttp
- Room / DataStore
- Hilt
- Flow collectors
- Firebase
- WorkManager
