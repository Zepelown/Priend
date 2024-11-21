package com.example.priend.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ContentInfoCompat.Flags
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.priend.R
import com.example.priend.databinding.ActivityLoginBinding
import com.example.priend.databinding.ActivitySelectPlantBinding
import com.example.priend.main.MainActivity
import com.google.android.datatransport.runtime.scheduling.jobscheduling.SchedulerConfig.Flag
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null

    private val binding get() = _binding!!

    private val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "로그인 실패 $error")
        } else if (token != null) {
            Log.e(TAG, "로그인 성공 ${token.accessToken}")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            _binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        binding.kakaoLoginButton.setOnClickListener {


// 카카오톡 설치 확인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                // 카카오톡 로그인
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    // 로그인 실패 부분
                    if (error != null) {
                        Log.e(TAG, "로그인 실패 $error")
                        // 사용자가 취소
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled ) {
                            return@loginWithKakaoTalk
                        }
                        // 다른 오류
                        else {
                            UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback) // 카카오 이메일 로그인
                        }
                    }
                    // 로그인 성공 부분
                    else if (token != null) {
                        Log.e(TAG, "로그인 성공 ${token.accessToken}")
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback) // 카카오 이메일 로그인
            }
        }

        setContentView(binding.root)
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        applicationContext.startActivity(intent)
    }
    companion object{
        const val TAG = "KAKAO"
    }
}