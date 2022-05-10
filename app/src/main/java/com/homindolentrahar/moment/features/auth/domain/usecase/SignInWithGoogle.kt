package com.homindolentrahar.moment.features.auth.domain.usecase

import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInWithGoogle @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(idToken: String, accessToken: String?): Flow<Resource<Unit>> =
        repository.signInWithGoogle(idToken, accessToken)
}