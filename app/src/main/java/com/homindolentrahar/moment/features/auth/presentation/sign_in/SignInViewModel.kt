package com.homindolentrahar.moment.features.auth.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.core.util.Resource
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

    private val _state = MutableStateFlow<Resource<Unit>>(Resource.Initial())
    val state: StateFlow<Resource<Unit>>
        get() = _state

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            signInWithEmailAndPassword(email, password)
                .onStart {
                    _state.value = Resource.Loading()
                }
                .catch { error ->
                    _state.value = Resource.Error(error.localizedMessage ?: "Unexpected Error")
                }
                .collect {
                    _state.value = Resource.Success(Unit)
                }
        }
    }

    fun googleSignIn(idToken: String, accessToken: String? = null) {
        viewModelScope.launch {
            signInWithGoogle(idToken, accessToken)
                .onStart {
                    _state.value = Resource.Loading()
                }
                .catch { error ->
                    _state.value = Resource.Error(error.localizedMessage ?: "Unexpected Error")
                }
                .collect {
                    _state.value = Resource.Success(Unit)
                }
        }
    }
}





