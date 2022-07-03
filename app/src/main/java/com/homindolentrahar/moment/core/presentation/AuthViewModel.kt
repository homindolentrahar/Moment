package com.homindolentrahar.moment.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Option
import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.auth.domain.model.AuthUser
import com.homindolentrahar.moment.features.auth.domain.usecase.GetAuthState
import com.homindolentrahar.moment.features.auth.domain.usecase.SignOut
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    getAuthState: GetAuthState,
    private val signOut: SignOut,
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<Unit>>(Resource.Initial())
    val state: StateFlow<Resource<Unit>>
        get() = _state

    val authState: Flow<Option<AuthUser>> = getAuthState()

    fun logout() {
        viewModelScope.launch {
            signOut()
                .onStart {
                    _state.value = Resource.Loading()
                }
                .catch { error ->
                    _state.value =
                        Resource.Error(error.localizedMessage?.toString() ?: "Unexpected error")
                }
                .collect {
                    _state.value = Resource.Success(Unit)
                }
        }
    }
}