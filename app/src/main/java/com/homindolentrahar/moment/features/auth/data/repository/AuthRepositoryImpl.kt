package com.homindolentrahar.moment.features.auth.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.auth.data.mapper.toDocumentSnapshot
import com.homindolentrahar.moment.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.homindolentrahar.moment.features.auth.data.remote.dto.AuthUserDto
import com.homindolentrahar.moment.features.auth.data.remote.dto.UserDto
import com.homindolentrahar.moment.features.auth.domain.model.AuthUser
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithGoogle(email: String): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun registerWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Resource<Unit>> =
        flow {
            try {
                emit(Resource.Loading())

                val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                val authUserDto = AuthUserDto.fromFirebaseUser(authResult.user!!)
                val userDto = UserDto.fromAuthUserDto(authUserDto)

                if (authResult.additionalUserInfo!!.isNewUser) {
                    firestore
                        .document(userDto.id)
                        .set(userDto.toDocumentSnapshot())
                        .await()
                }

                emit(Resource.Success(Unit))
            } catch (exception: Exception) {
                emit(Resource.Error(exception.localizedMessage ?: "Unexpected error"))
            }
        }

    override fun getCurrentUser(): Resource<AuthUser> {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

}