# TaskFlow

Production-style Android project for **Android Advanced Techniques**.

**Chapter 6 — Dependency Injection with Hilt**

Chapter 5 layers stay. Hilt builds the graph. `ProductGraph` is gone.

```text
@HiltAndroidApp TaskFlowApp
        ↓
@AndroidEntryPoint MainActivity
        ↓
hiltViewModel()
        ↓
@HiltViewModel Login / Dashboard / ProductSearch
        ↓
@Inject use cases
        ↓
@Binds ProductRepository / DashboardRepository
        ↓
@Inject impls + fake sources
```

## What Hilt provides

| Type | How |
|---|---|
| Use cases | `@Inject constructor` |
| `ProductRepository` / `DashboardRepository` | `@Binds` + `@Singleton` |
| Fake sources / impls | `@Inject constructor` |
| ViewModels | `@HiltViewModel` |

No Retrofit or Room this chapter. Those modules arrive in Chapters 7–8 with `@Provides`.

## How to demo

Same as Chapter 5: login → dashboard **Retry** → **Search**. Type `error` for the search error state.

## Carried forward

Flavors, signing, `Resource`, login, parallel dashboard, Flow search, Clean Architecture layers.

## Not in this chapter

- Retrofit / OkHttp
- Room / DataStore
- Firebase
