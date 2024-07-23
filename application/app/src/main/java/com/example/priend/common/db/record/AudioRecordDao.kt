package com.example.priend.common.db.record

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AudioRecordDao {
    @Insert
    suspend fun insert(audioRecordEntity: AudioRecordEntity)

    @Delete
    suspend fun delete(audioRecordEntity: AudioRecordEntity)

    @Query("SELECT * FROM audioRecord WHERE id = :id")
    suspend fun getAudioRecordById(id : Int) : AudioRecordEntity?


}