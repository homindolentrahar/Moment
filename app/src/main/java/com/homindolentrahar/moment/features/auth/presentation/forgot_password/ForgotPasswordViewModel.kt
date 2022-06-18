package com.homindolentrahar.moment.features.auth.presentation.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.features.auth.domain.usecase.SendPasswordResetEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val sendPasswordResetEmail: SendPasswordResetEmail
) : ViewModel() {

    private val _state = MutableStateFlow(ForgotPasswordState())
    val state: StateFlow<ForgotPasswordState>
        get() = _state

    fun resetPassword(email: String) {
        viewModelScope.launch {
            sendPasswordResetEmail(email)
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