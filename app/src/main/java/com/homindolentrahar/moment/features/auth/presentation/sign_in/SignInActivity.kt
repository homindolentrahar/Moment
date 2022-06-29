package com.homindolentrahar.moment.features.auth.presentation.sign_in

import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.homindolentrahar.moment.MainActivity
import com.homindolentrahar.moment.R
import com.homindolentrahar.moment.databinding.ActivitySignInBinding
import com.homindolentrahar.moment.features.auth.presentation.forgot_password.ForgotPasswordActivity
import com.homindolentrahar.moment.features.auth.presentation.sign_up.SignUpActivity
import com.homindolentrahar.moment.features.auth.util.RegexPattern
import com.homindolentrahar.moment.features.transaction.presentation.transaction_home.TransactionHomeActivity
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {
    @Inject
    lateinit var oneTapClient: SignInClient

    @Inject
    lateinit var signInRequest: BeginSignInRequest

    private lateinit var binding: ActivitySignInBinding
    private val viewModel: SignInViewModel by viewModels()
    private val TAG = SignInActivity::class.java.simpleName
    private val ONE_TAP_REQUEST = 212

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)

        setContentView(binding.root)

        signWithGoogle()

        binding.btnSignIn.setOnClickListener {
            signIn()
        }

        binding.btnGoogleLogin.setOnClickListener {
            signWithGoogle()
        }

        binding.tvBtnForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        binding.tvBtnSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (state.loading) {
                        Log.d(TAG, "Loading...")

                        Toasty.custom(
                            this@SignInActivity,
                            "Signing In",
                            R.drawable.loading,
                            R.color.black,
                            Toast.LENGTH_LONG,
                            true,
                            true
                        )
                            .show()
                    } else if (state.error.isNotBlank()) {
                        Log.d(TAG, "Error: ${state.error}")

                        Toasty.error(this@SignInActivity, "Login Failed", Toast.LENGTH_LONG, true)
                            .show()
                    } else if (!state.loading && state.error.isNotBlank()) {
                        Log.d(TAG, "Login success!")

                        startActivity(
                            Intent(
                                this@SignInActivity,
                                TransactionHomeActivity::class.java
                            )
                        )
                        finish()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            ONE_TAP_REQUEST -> {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
                    val idToken = credential.googleIdToken

                    when {
                        idToken != null -> {
                            Log.d(TAG, "ID Token: $idToken")

                            viewModel.googleSignIn(idToken)
                        }
                        else -> {
                            Log.e(TAG, "ID Token not found!")
                        }
                    }
                } catch (e: ApiException) {
                    when (e.statusCode) {
                        CommonStatusCodes.CANCELED -> {
                            Log.d(TAG, "One tap cancelled")
                        }
                        else -> {
                            Log.d(TAG, e.localizedMessage!!.toString())
                        }
                    }
                }
            }
        }
    }

    private fun signIn() {
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()

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
            binding.editTextPassword.error = "Password is invalid"
            return
        }

        viewModel.signIn(email, password)
    }

    private fun signWithGoogle() {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                try {
//                    val intent = IntentSenderRequest.Builder(result.pendingIntent.intentSender)
//                        .setFillInIntent(null)
//                        .setFlags(0, 0)
//                        .build()
                    startIntentSenderForResult(
                        result.pendingIntent.intentSender,
                        ONE_TAP_REQUEST,
                        null,
                        0, 0, 0,
                        null
                    )

                } catch (exception: IntentSender.SendIntentException) {
                    Log.e(TAG, "Couldn't start One Tap UI: ${exception.localizedMessage}")
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, exception.localizedMessage!!)
            }
    }
}