package com.homindolentrahar.moment.features.auth.domain.usecase

import arrow.core.Option
import com.homindolentrahar.moment.features.auth.domain.model.AuthUser
import com.homindolentrahar.moment.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAuthState @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<Option<AuthUser>> =
        repository.getAuthState()
}