# TaskFlow

**Chapter 8 — Retrofit + OkHttp**

Login goes through Retrofit. A classroom mock interceptor returns JSON. The auth interceptor attaches `Bearer` after login.

```text
LoginViewModel → LoginUseCase → AuthRepositoryImpl → TaskFlowApi
        ↓
TokenStore (DataStore) → AuthInterceptor → Authorization header
```

Demo credentials stay `student@example.com` / `123456`. Wrong password returns 401.

## Carried forward

Room notes, DataStore theme, Hilt, flavors.

## Not a public server

`ClassroomMockInterceptor` is the backend for class. Inspector still shows the Bearer header on `/profile/me`.
