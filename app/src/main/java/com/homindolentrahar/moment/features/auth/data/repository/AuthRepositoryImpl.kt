package com.homindolentrahar.moment.features.auth.data.repository

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.auth.data.mapper.toAuthUser
import com.homindolentrahar.moment.features.auth.data.mapper.toDocumentSnapshot
import com.homindolentrahar.moment.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.homindolentrahar.moment.features.auth.data.remote.dto.AuthUserDto
import com.homindolentrahar.moment.features.auth.data.remote.dto.UserDto
import com.homindolentrahar.moment.features.auth.domain.model.AuthUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : AuthRepository {

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Resource<Unit>> =
        flow {
            try {
                emit(Resource.Loading())

                auth.signInWithEmailAndPassword(email, password).await()

                emit(Resource.Success(Unit))
            } catch (exception: Exception) {
                emit(Resource.Error(exception.localizedMessage ?: "Unexpected error"))
            }
        }

    override suspend fun signInWithGoogle(
        idToken: String,
        accessToken: String?
    ): Flow<Resource<Unit>> =
        flow {
            try {
                emit(Resource.Loading())

                val googleCredential = GoogleAuthProvider.getCredential(idToken, accessToken)
                val authResult = auth.signInWithCredential(googleCredential).await()
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

    override suspend fun signOut(): Flow<Resource<Unit>> =
        flow {
            try {
                emit(Resource.Loading())

                auth.currentUser?.apply {
                    delete().await()

                    auth.signOut()

                    emit(Resource.Success(Unit))
                }
            } catch (exception: Exception) {
                emit(Resource.Error(exception.localizedMessage ?: "Unexpected error"))
            }
        }

    override fun getCurrentUser(): Option<AuthUser> {
        val currentUser = auth.currentUser

        currentUser?.let { user ->
            val authUserDto = AuthUserDto.fromFirebaseUser(user)

            return Some(authUserDto.toAuthUser())
        }

        return None
    }

    override fun getAuthState(): Flow<Option<AuthUser>> =
        callbackFlow {
            val authStateListener = FirebaseAuth.AuthStateListener {
                val authUser = it.currentUser?.let { user ->
                    val authUserDto = AuthUserDto.fromFirebaseUser(user)

                    Some(authUserDto.toAuthUser())
                } ?: None

                trySend(authUser)
            }

            auth.addAuthStateListener(authStateListener)

            awaitClose {
                auth.removeAuthStateListener(authStateListener)
            }
        }


}