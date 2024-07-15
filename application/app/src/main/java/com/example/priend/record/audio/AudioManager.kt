package com.example.priend.record.audio

import android.util.Log
import java.io.FileDescriptor

class AudioManager {
    private var audioRecorder : AudioRecorder = AudioRecorder()
    private var audioConverter: AudioConverter = AudioConverter()


    fun startRecord(fileDescriptor: FileDescriptor){
        audioRecorder.record(fileDescriptor)
        Log.d("record", "녹음 시작")
    }
    fun stopRecord(outputFilePath : String){
        audioRecorder.stop()
        audioConverter.convertToWav(outputFilePath)
        Log.d("record", "녹음 중지")
    }

}