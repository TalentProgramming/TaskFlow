# TaskFlow

**Chapter 8 — Retrofit + OkHttp**

Login goes through Retrofit to the live classroom API:

`https://taskflowapi-7jb0.onrender.com/v1/`

```text
LoginViewModel → LoginUseCase → AuthRepositoryImpl → TaskFlowApi
        ↓
TokenStore (DataStore) → AuthInterceptor → Authorization: Bearer
        ↓
https://taskflowapi-7jb0.onrender.com
```

Demo credentials: `student@example.com` / `123456`. Wrong password returns 401.

The first call after Render sleeps can take ~30s. Timeouts are 60s so that cold start can finish. Open the URL in a browser first if login still fails.

`ClassroomMockInterceptor` is not on the OkHttp chain anymore. The phone calls the real host.
