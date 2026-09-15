# Changelog

## 1.1.0 — versionCode 2

First classroom release candidate.

- GitHub Actions runs `test` then `assembleStagingDebug` on every push and PR
- Profile banner prints `versionName` and `versionCode`
- Capstone checklist and store/AAB notes live in the README

Play Console and Firebase App Distribution both require this integer to increase on every upload. Do not reuse `2`.
