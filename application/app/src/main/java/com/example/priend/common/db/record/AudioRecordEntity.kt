package com.example.priend.common.db.record

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audioRecord")
data class AudioRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title : String,
    val fileUriString: String
){
    val fileUri: Uri
        get() = Uri.parse(fileUriString)
}