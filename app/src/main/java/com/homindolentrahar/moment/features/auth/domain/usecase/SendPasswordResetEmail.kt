package com.homindolentrahar.moment.features.auth.domain.usecase

import com.homindolentrahar.moment.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SendPasswordResetEmail @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String): Flow<Unit> = flow {
        emit(repository.sendPasswordResetEmail(email))
    }
}