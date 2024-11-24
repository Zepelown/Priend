package com.example.priend.common

import com.arthenica.mobileffmpeg.BuildConfig
import com.example.priend.main.info.data.repository.InfoRepository
import com.example.priend.main.info.data.source.InfoSource
import com.example.priend.stt.data.repository.SttRepository
import com.example.priend.stt.data.source.SttService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY // 디버그 모드에서는 요청 및 응답 바디를 로그로 출력
            } else {
                HttpLoggingInterceptor.Level.NONE // 릴리즈 모드에서는 로그를 출력하지 않음
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // 로깅 인터셉터 추가
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSttService(retrofit: Retrofit): SttService {
        return retrofit.create(SttService::class.java)
    }

    @Singleton
    @Provides
    fun provideSttRepository(sttService: SttService) = SttRepository(sttService)

    @Singleton
    @Provides
    fun provideInfoService(retrofit: Retrofit): InfoSource = retrofit.create(InfoSource::class.java)

    @Singleton
    @Provides
    fun provideInfoRepository(infoSource: InfoSource) : InfoRepository = InfoRepository(infoSource)

}