package com.homindolentrahar.moment.features.auth.domain.repository

import arrow.core.Option
import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.auth.domain.model.AuthUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    )

    suspend fun signInWithGoogle(
        idToken: String,
        accessToken: String?
    )

    suspend fun registerWithEmailAndPassword(
        email: String,
        password: String
    )

    suspend fun forgotPassword(
        email: String
    )

    suspend fun signOut()

    fun getCurrentUser(): Option<AuthUser>

    fun getAuthState(): Flow<Option<AuthUser>>
}