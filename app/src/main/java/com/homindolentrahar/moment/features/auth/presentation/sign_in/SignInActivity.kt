package com.homindolentrahar.moment.features.auth.presentation.sign_in

import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.homindolentrahar.moment.R
import com.homindolentrahar.moment.databinding.ActivitySignInBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {
    @Inject
    lateinit var oneTapClient: SignInClient

    @Inject
    lateinit var signInRequest: BeginSignInRequest

    private lateinit var binding: ActivitySignInBinding
    private val TAG = SignInActivity::class.java.simpleName

    private val activityForResult = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) {
        val credential = oneTapClient.getSignInCredentialFromIntent(it.data)
        val idToken = credential.googleIdToken

        when {
            idToken != null -> {
                Log.d(TAG, "ID Token: $idToken")
            }
            else -> {
                Log.e(TAG, "ID Token not found!")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)

        setContentView(binding.root)

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                try {
                    val intent = IntentSenderRequest.Builder(result.pendingIntent.intentSender)
                        .setFillInIntent(null)
                        .setFlags(0, 0)
                        .build()

                    activityForResult.launch(intent)
                } catch (exception: IntentSender.SendIntentException) {
                    Log.e(TAG, "Couldn't start One Tap UI: ${exception.localizedMessage}")
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, exception.localizedMessage!!)
            }
    }
}