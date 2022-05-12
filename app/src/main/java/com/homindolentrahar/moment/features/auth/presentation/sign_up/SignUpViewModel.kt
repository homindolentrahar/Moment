package com.homindolentrahar.moment.features.auth.presentation.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.auth.domain.usecase.RegisterWithEmailAndPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerWithEmailAndPassword: RegisterWithEmailAndPassword
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState>
        get() = _state

    fun register(email: String, password: String) {
        viewModelScope.launch {
            registerWithEmailAndPassword(email, password)
                .collect { resource ->
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