package com.homindolentrahar.moment.features.auth.presentation.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.features.auth.domain.usecase.RegisterWithEmailAndPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerWithEmailAndPassword: RegisterWithEmailAndPassword
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState>
        get() = _state

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            registerWithEmailAndPassword(name, email, password)
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