# TaskFlow

**Chapter 8 — Retrofit + OkHttp**

The app calls the live Render hosts. No classroom mock interceptor.

| Flavor | `API_BASE_URL` |
|---|---|
| staging / uat | `https://taskflowapi-7jb0.onrender.com/v1/` |
| prod | `https://taskflowapiprod.onrender.com/` |

| Method | Path | Auth |
|---|---|---|
| POST | `auth/login` | no |
| GET | `profile/me` | Bearer |
| GET | `posts` | Bearer |
| PUT | `profile/me` | Bearer |

```text
Login → TokenStore → AuthInterceptor (Bearer)
Dashboard → GET profile/me + GET posts in parallel
```

Demo: `student@example.com` / `123456`

Redeploy `api/` so Render serves `GET /posts`. Timeouts are 60s for a cold start.
