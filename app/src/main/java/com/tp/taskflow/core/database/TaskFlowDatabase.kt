package com.tp.taskflow.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tp.taskflow.feature.chat.data.ChatDao
import com.tp.taskflow.feature.chat.data.ChatMessageEntity
import com.tp.taskflow.feature.note.data.NoteDao
import com.tp.taskflow.feature.note.data.NoteEntity
import com.tp.taskflow.feature.product.data.ProductDao
import com.tp.taskflow.feature.product.data.ProductEntity

@Database(
    entities = [NoteEntity::class, ProductEntity::class, ChatMessageEntity::class],
    version = 3,
    exportSchema = false
)
abstract class TaskFlowDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun productDao(): ProductDao
    abstract fun chatDao(): ChatDao
}
