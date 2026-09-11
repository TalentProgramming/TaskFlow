# TaskFlow

Production-style Android project for **Android Advanced Techniques**.

**Chapter 5 — Clean Architecture with Advanced MVVM**

Chapter 4 search still works. The ViewModel no longer talks to a fake repository class. It talks to a use case.

```text
ProductSearchScreen
        ↓
ProductSearchViewModel
        ↓
SearchProductsUseCase / GetProductsUseCase
        ↓
ProductRepository (interface)
        ↓
ProductRepositoryImpl + ProductDto + toDomain()
```

## Who depends on whom

| Layer | Types | May import |
|---|---|---|
| presentation | Screen, ViewModel | domain use cases, `Product`, `Resource` |
| domain | `Product`, `ProductRepository`, use cases | Kotlin / Flow / `Resource` only |
| data | `ProductDto`, mapper, `ProductRepositoryImpl` | domain contracts |
| di | `ProductGraph` | data impl + domain use cases |

The ViewModel must not import `ProductDto` or `ProductRepositoryImpl`.

## How to demo

Same as Chapter 4: login → dashboard → **Search**. Debounce, Empty, and `error` still work.

## Carried forward

Flavors, signing, `Resource`, login, parallel dashboard, Flow search.

## Not in this chapter

- Hilt (Chapter 6)
- Retrofit / Room
- Firebase
