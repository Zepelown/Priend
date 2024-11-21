package com.example.priend.common

import android.app.Application
import com.example.priend.BuildConfig
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PriendApp : Application(){
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this,BuildConfig.KAKAKO_APP_KEY)
    }
}