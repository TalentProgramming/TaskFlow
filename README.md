# TaskFlow

**Chapter 14 — Firebase Auth (email/password), Firestore, Remote Config, Crashlytics**

No Google Sign-In. No Storage. No Analytics. No FCM.

## Firebase console

1. Add Android apps: `com.tp.taskflow`, `com.tp.taskflow.staging`, `com.tp.taskflow.uat`.
2. Put `google-services.json` in `app/`. Do not commit it.
3. Authentication → **Email/Password** only.
4. Firestore → deploy `firestore.rules`.
5. Crashlytics → enable.
6. Remote Config — create and **Publish** these two keys:
   - `chat_enabled` (Boolean, default `true`) — hides the Chat tab when `false`
   - `crash_enabled` (Boolean, default `true`) — hides Home **Test crash** when `false`

## What the app does

| Product | Where |
|---|---|
| Auth | After classroom login, Firebase signs in or creates that email/password user |
| Firestore | Chat tab `messages` snapshot listener |
| Remote Config | `chat_enabled`, `crash_enabled` |
| Crashlytics | user id, non-fatal on failures, Home Test crash |

Demo login: `student@example.com` / `123456`.
