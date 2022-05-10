package com.homindolentrahar.moment.features.auth.domain.usecase

import arrow.core.Option
import com.homindolentrahar.moment.features.auth.domain.model.AuthUser
import com.homindolentrahar.moment.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUser @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Option<AuthUser> =
        repository.getCurrentUser()
}