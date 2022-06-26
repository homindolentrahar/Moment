package com.homindolentrahar.moment.features.auth.presentation.forgot_password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.homindolentrahar.moment.databinding.ActivityForgotPasswordBinding
import dagger.hilt.android.HiltAndroidApp
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch

@HiltAndroidApp
class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private val viewModel: ForgotPasswordViewModel by viewModels()
    private val TAG = ForgotPasswordViewModel::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnRecoverPassword.setOnClickListener {
            recoverPassword()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { state ->
                    if (state.loading) {
                        Log.d(TAG, "Loading...")
                    } else if (state.error.isNotBlank()) {
                        Log.d(TAG, "Error: ${state.error}")

                        Toasty.error(
                            this@ForgotPasswordActivity,
                            "Failed to send reset password email",
                            Toast.LENGTH_LONG,
                            true
                        )
                            .show()
                    } else {
                        Log.d(TAG, "Email sent")

                        Toasty.success(
                            this@ForgotPasswordActivity,
                            "Reset Password has been sent to your email",
                            Toast.LENGTH_LONG,
                            true
                        )
                    }
                }
            }
        }
    }

    private fun recoverPassword() {
        val email = binding.editTextEmail.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.error = "Email is invalid!"
            return
        }

        viewModel.resetPassword(email)
    }
}