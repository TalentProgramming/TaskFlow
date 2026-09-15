package com.tp.taskflow.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.tp.taskflow.core.database.TaskFlowDatabase
import com.tp.taskflow.feature.note.data.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.settingsDataStore by preferencesDataStore(name = "settings")

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.settingsDataStore

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TaskFlowDatabase =
        Room.databaseBuilder(context, TaskFlowDatabase::class.java, "taskflow.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideNoteDao(database: TaskFlowDatabase): NoteDao = database.noteDao()
}
