package com.homindolentrahar.moment.features.auth.data.repository

import arrow.core.getOrElse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.auth.data.mapper.toDocumentSnapshot
import com.homindolentrahar.moment.features.auth.domain.model.User
import com.homindolentrahar.moment.features.auth.domain.repository.AuthRepository
import com.homindolentrahar.moment.features.auth.util.EmailAddress
import com.homindolentrahar.moment.features.auth.util.Password
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.homindolentrahar.moment.features.auth.data.remote.dto.AuthUserDto
import com.homindolentrahar.moment.features.auth.data.remote.dto.UserDto

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun signInWithEmailAndPassword(
        email: EmailAddress,
        password: Password
    ): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithGoogle(email: EmailAddress): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun registerWithEmailAndPassword(
        email: EmailAddress,
        password: Password
    ): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())

            val emailStr = email.value.getOrElse {
                emit(Resource.Error("Invalid email address"))
            }.toString()
            val passwordStr = password.value.getOrElse {
                emit(Resource.Error("Invalid password"))
            }.toString()

            val registerTask = auth.createUserWithEmailAndPassword(
                emailStr, passwordStr
            )

            if (registerTask.isSuccessful) {
                auth.currentUser?.let { currentUser ->
                    val authUserDto = AuthUserDto.fromFirebaseUser(currentUser)
                    val userDto = UserDto.fromAuthUserDto(authUserDto)

                    val storingUserDataTask =
                        firestore.document(userDto.id).set(userDto.toDocumentSnapshot())

                    if (storingUserDataTask.isSuccessful) {
                        emit(Resource.Success(Unit))
                    } else {
                        val errorMessage = storingUserDataTask.exception!!.localizedMessage!!

                        emit(Resource.Error(errorMessage))
                    }
                }
            } else {
                val errorMessage = registerTask.exception!!.localizedMessage!!

                emit(Resource.Error(errorMessage))
            }
        }
    }

    override fun getCurrentUser(): Resource<User> {
        TODO("Not yet implemented")
    }

    override fun signOut(): Resource<Unit> {
        TODO("Not yet implemented")
    }

}