# TaskFlow

**Chapter 9 — Offline-first + Paging 3**

Products refresh from Retrofit into Room. The list is a `PagingSource`. Airplane mode still shows the cached table.

The classroom catalog is **40 products** in pages of **8** (`?page=1` … `?page=5`). Scroll the search list to load later pages.

Type `error` to force HTTP 500.

## Demo

Search → list comes from Room. Kill network conceptually (mock still works); Database Inspector shows `products`.
