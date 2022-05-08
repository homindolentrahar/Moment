package com.homindolentrahar.moment.features.auth.data.remote.dto

import com.google.firebase.auth.FirebaseUser

data class AuthUserDto(
    val uid: String,
    val name: String?,
    val email: String?,
    val phone: String?,
    val createdAt: Long,
    val lastSignedInAt: Long
) {
    companion object {
        fun fromFirebaseUser(firebaseUser: FirebaseUser): AuthUserDto {
            return AuthUserDto(
                uid = firebaseUser.uid,
                name = firebaseUser.displayName,
                email = firebaseUser.email,
                phone = firebaseUser.phoneNumber,
                createdAt = firebaseUser.metadata!!.creationTimestamp,
                lastSignedInAt = firebaseUser.metadata!!.lastSignInTimestamp
            )
        }
    }
}
