package com.homindolentrahar.moment.features.auth.domain.repository

import arrow.core.Option
import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.auth.domain.model.AuthUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Resource<Unit>>

    suspend fun signInWithGoogle(
        idToken: String,
        accessToken: String?
    ): Flow<Resource<Unit>>

    suspend fun registerWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Resource<Unit>>

    suspend fun signOut(): Flow<Resource<Unit>>

    fun getCurrentUser(): Option<AuthUser>

    fun getAuthState(): Flow<Option<AuthUser>>
}