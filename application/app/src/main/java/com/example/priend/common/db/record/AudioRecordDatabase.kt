package com.example.priend.common.db.record

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AudioRecordEntity::class], version = 1)
abstract class AudioRecordDatabase : RoomDatabase() {
    abstract fun audioRecordDao() : AudioRecordDao
}