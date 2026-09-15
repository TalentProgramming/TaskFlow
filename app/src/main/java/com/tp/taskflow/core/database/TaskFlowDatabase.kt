package com.tp.taskflow.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tp.taskflow.feature.note.data.NoteDao
import com.tp.taskflow.feature.note.data.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TaskFlowDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
