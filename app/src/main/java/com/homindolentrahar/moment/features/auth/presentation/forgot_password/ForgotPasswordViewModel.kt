package com.homindolentrahar.moment.features.auth.presentation.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.core.util.Resource
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

    private val _state = MutableStateFlow<Resource<Unit>>(Resource.Initial())
    val state: StateFlow<Resource<Unit>>
        get() = _state

    fun resetPassword(email: String) {
        viewModelScope.launch {
            sendPasswordResetEmail(email)
                .onStart {
                    _state.value = Resource.Loading()
                }
                .catch { error ->
                    _state.value = Resource.Error(error.localizedMessage ?: "Unexpected error")
                }
                .collect {
                    _state.value = Resource.Success(Unit)
                }
        }
    }

}