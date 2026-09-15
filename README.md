# TaskFlow

**Chapter 7 — Room Database & DataStore**

Notes are stored in Room. Dark mode is stored in DataStore. Kill the app; both survive.

```text
NotesScreen → NotesViewModel → use cases → NoteRepository
        ↓
NoteRepositoryImpl → NoteDao → TaskFlowDatabase
```

Theme: `ThemeSettings` → Preferences DataStore → `ThemeViewModel` → `TaskFlowTheme`.

## Demo

1. Login → dashboard.
2. Toggle **Dark**. Restart. Theme stays.
3. Open **Notes**. Add / search / delete. Kill the app. Notes stay.

## Carried forward

Flavors, Hilt, Clean layers, login, dashboard, Flow search.

## Not yet

Retrofit (Chapter 8), Paging (Chapter 9).
