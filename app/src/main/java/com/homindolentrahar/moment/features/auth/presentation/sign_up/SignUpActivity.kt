package com.homindolentrahar.moment.features.auth.presentation.sign_up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.homindolentrahar.moment.MainActivity
import com.homindolentrahar.moment.R
import com.homindolentrahar.moment.databinding.ActivitySignUpBinding
import com.homindolentrahar.moment.features.auth.presentation.sign_in.SignInActivity
import com.homindolentrahar.moment.features.auth.util.RegexPattern
import dagger.hilt.android.HiltAndroidApp
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch

@HiltAndroidApp
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()
    private val TAG = SignUpActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            signUp()
        }

        binding.tvBtnSignin.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (state.loading) {
                        Log.d(TAG, "Loading...")
                    } else if (state.error.isNotBlank()) {
                        Log.d(TAG, "Error: ${state.error}")

                        Toasty.error(
                            this@SignUpActivity,
                            "Register Failed",
                            Toast.LENGTH_LONG,
                            true
                        )
                            .show()
                    } else {
                        Log.d(TAG, "Login success!")

                        startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }

    private fun signUp() {
        val name = binding.editTextName.text.toString().trim()
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()

        if (name.isEmpty()) {
            binding.editTextName.error = "Name cannot be empty!"
            return
        }

        if (!RegexPattern.NAME_PATTERN.matcher(name).matches()) {
            binding.editTextName.error = "Name is invalid!"
            return
        }

        if (email.isEmpty()) {
            binding.editTextEmail.error = "Email cannot be empty!"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.error = "Email is invalid!"
            return
        }

        if (password.isEmpty()) {
            binding.editTextPassword.error = "Password cannot be empty!"
            return
        }

        if (!RegexPattern.PASSWORD_PATTERN.matcher(password).matches()) {
            binding.editTextPassword.error = "Password is invalid!"
            return
        }

        viewModel.register(name, email, password)
    }
}