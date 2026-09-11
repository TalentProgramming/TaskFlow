# TaskFlow

Production-style Android project for **Android Advanced Techniques**.

**Chapter 4 — Kotlin Flow, StateFlow & SharedFlow**

This branch keeps Chapters 1–3 (flavors, signing, login, parallel dashboard) and adds a product search screen driven by Flow.

## How to demo

1. Sign in with `student@example.com` / `123456`.
2. Open the dashboard, Retry if posts fail once, then tap **Search**.
3. Type slowly — after 500 ms the list filters.
4. Type a missing word (for example `zzzz`) for **Empty**.
5. Type `error` for **Error**.
6. Tap a product to see a SharedFlow snackbar.

## Flow pipeline

```text
query: StateFlow<String>
   → debounce(500)
   → distinctUntilChanged
   → flatMapLatest(repository.search)
   → StateFlow<Resource<List<Product>>>
```

The search box is **not** stored only in the Composable. Collection uses `collectAsStateWithLifecycle`.

| Query | Result |
|---|---|
| blank | All fake products |
| `book` | Kotlin Handbook, Android Workbook |
| `zzzz` | Empty |
| `error` | Error — Search service unavailable |

## Carried forward

| From | Still here |
|---|---|
| Chapter 1 | `staging` / `uat` / `prod` flavors and release signing |
| Chapter 2 | `Resource<T>` and the login simulator |
| Chapter 3 | Parallel dashboard with `async` / `await` |

## Not in this chapter

- Retrofit / OkHttp
- Room / DataStore
- Hilt
- Firebase
