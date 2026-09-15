# TaskFlow

**Chapter 9 — Offline-first + Paging 3**

Products refresh from Retrofit into Room. The list is a `PagingSource`. Airplane mode still shows the cached table.

The classroom catalog is **40 products** in pages of **8**. A `RemoteMediator` loads one page at a time (about 800 ms). Scroll to see “Loading next page…”.

Type `error` to force HTTP 500.

## Demo

Search → list comes from Room. Kill network conceptually (mock still works); Database Inspector shows `products`.
