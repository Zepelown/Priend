package com.example.priend.common.db.record

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AudioRecordDatabaseModule {
    @Provides
    fun provideRecordHistoryDao(db : AudioRecordDatabase)
            = db.audioRecordDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : AudioRecordDatabase
            = Room.databaseBuilder(context, AudioRecordDatabase::class.java, "audio_record.db").build()
}