package com.homindolentrahar.moment.features.auth.domain.usecase

import com.homindolentrahar.moment.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInWithGoogle @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(idToken: String, accessToken: String?): Flow<Unit> = flow {
        emit(repository.signInWithGoogle(idToken, accessToken))
    }
}