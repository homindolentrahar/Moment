package com.homindolentrahar.moment.features.auth.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.features.auth.domain.usecase.SignInWithEmailAndPassword
import com.homindolentrahar.moment.features.auth.domain.usecase.SignInWithGoogle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInWithEmailAndPassword: SignInWithEmailAndPassword,
    private val signInWithGoogle: SignInWithGoogle,
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState>
        get() = _state

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            signInWithEmailAndPassword(email, password)
                .onStart {
                    _state.value = _state.value.copy(
                        loading = true,
                        error = ""
                    )
                }
                .catch { error ->
                    _state.value = _state.value.copy(
                        loading = false,
                        error = error.localizedMessage!!.toString()
                    )
                }
                .collect {
                    _state.value = _state.value.copy(
                        loading = false,
                        error = ""
                    )
                }
        }
    }

    fun googleSignIn(idToken: String, accessToken: String? = null) {
        viewModelScope.launch {
            signInWithGoogle(idToken, accessToken)
                .onStart {
                    _state.value = _state.value.copy(
                        loading = true,
                        error = ""
                    )
                }
                .catch { error ->
                    _state.value = _state.value.copy(
                        loading = false,
                        error = error.localizedMessage!!.toString()
                    )
                }
                .collect {
                    _state.value = _state.value.copy(
                        loading = false,
                        error = ""
                    )
                }
        }
    }
}





