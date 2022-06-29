package com.homindolentrahar.moment.core.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import arrow.core.None
import arrow.core.Some
import com.homindolentrahar.moment.MainActivity
import com.homindolentrahar.moment.databinding.ActivitySplashBinding
import com.homindolentrahar.moment.features.auth.presentation.sign_in.SignInActivity
import com.homindolentrahar.moment.features.transaction.presentation.transaction_home.TransactionHomeActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { true }
        }

        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { option ->
                    val intent = when (option) {
                        is None -> {
                            Log.d(SplashActivity::class.java.simpleName, "Unauthenticated")
//                            Intent(this@SplashActivity, SignInActivity::class.java)
                            Intent(this@SplashActivity, TransactionHomeActivity::class.java)
                        }
                        is Some -> {
                            Log.d(SplashActivity::class.java.simpleName, "Authenticated")
                            Intent(this@SplashActivity, TransactionHomeActivity::class.java)
                        }
                    }

                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}