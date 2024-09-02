package com.example.priend.common.db.record

import javax.inject.Inject

class AudioRecordRepository @Inject constructor(
    private val audioRecordDatabase: AudioRecordDatabase
){
    private val audioRecordDao = audioRecordDatabase.audioRecordDao()

    suspend fun insertAudioRecord(audioRecordEntity: AudioRecordEntity) = audioRecordDao.insert(audioRecordEntity)

    suspend fun getAudioRecordById(id : Int) = audioRecordDao.getAudioRecordById(id)
}