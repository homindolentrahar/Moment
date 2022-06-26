package com.homindolentrahar.moment.features.auth.data.repository

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.homindolentrahar.moment.features.auth.data.mapper.toAuthUser
import com.homindolentrahar.moment.features.auth.data.mapper.toDocumentSnapshot
import com.homindolentrahar.moment.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
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
    ) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signInWithGoogle(
        idToken: String,
        accessToken: String?
    ) {
        val googleCredential = GoogleAuthProvider.getCredential(idToken, accessToken)
        val authResult = auth.signInWithCredential(googleCredential).await()
        val authUserDto = AuthUserDto.fromFirebaseUser(authResult.user!!)
        val userDto = UserDto.fromAuthUserDto(authUserDto)

        if (authResult.additionalUserInfo!!.isNewUser) {
            firestore
                .collection(UserDto.COLLECTION)
                .document(userDto.id)
                .set(userDto.toDocumentSnapshot())
                .await()
        }
    }

    override suspend fun registerWithEmailAndPassword(
        name: String,
        email: String,
        password: String
    ) {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        val authUserDto = AuthUserDto.fromFirebaseUser(authResult.user!!)
        val userDto = UserDto.fromAuthUserDto(authUserDto, name = name)

        if (authResult.additionalUserInfo!!.isNewUser) {
            firestore
                .collection(UserDto.COLLECTION)
                .document(userDto.id)
                .set(userDto.toDocumentSnapshot())
                .await()
        }

    }

    override suspend fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun signOut() {
        auth.currentUser?.apply {
            delete().await()

            auth.signOut()

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