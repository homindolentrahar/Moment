package com.homindolentrahar.moment.features.auth.domain.repository

import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.auth.domain.model.User
import com.homindolentrahar.moment.features.auth.util.EmailAddress
import com.homindolentrahar.moment.features.auth.util.Password
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signInWithEmailAndPassword(
        email: EmailAddress,
        password: Password
    ): Flow<Resource<Unit>>

    suspend fun signInWithGoogle(email: EmailAddress): Flow<Resource<Unit>>

    suspend fun registerWithEmailAndPassword(
        email: EmailAddress,
        password: Password
    ): Flow<Resource<Unit>>

    fun getCurrentUser(): Resource<User>

    fun signOut(): Resource<Unit>
}