package com.homindolentrahar.moment.core.presentation.splash

import androidx.lifecycle.ViewModel
import arrow.core.Option
import com.homindolentrahar.moment.features.auth.domain.model.AuthUser
import com.homindolentrahar.moment.features.auth.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    authRepository: AuthRepository
) : ViewModel() {

    val state: Flow<Option<AuthUser>> = authRepository.getAuthState()

}