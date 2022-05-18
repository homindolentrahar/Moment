package com.homindolentrahar.moment.features.auth.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.auth.domain.usecase.SignInWithEmailAndPassword
import com.homindolentrahar.moment.features.auth.domain.usecase.SignInWithGoogle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
            signInWithEmailAndPassword(
                email = email,
                password = password
            ).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = resource.message ?: "Unexpected error",
                            loading = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            loading = resource.isLoading
                        )
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            loading = false,
                            error = ""
                        )
                    }
                }
            }
        }
    }

    fun googleSignIn(idToken: String, accessToken: String? = null) {
        viewModelScope.launch {
            signInWithGoogle(
                idToken = idToken,
                accessToken = accessToken
            ).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = resource.message ?: "Unexpected error",
                            loading = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            loading = resource.isLoading
                        )
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            loading = false,
                            error = ""
                        )
                    }
                }
            }
        }
    }
}





