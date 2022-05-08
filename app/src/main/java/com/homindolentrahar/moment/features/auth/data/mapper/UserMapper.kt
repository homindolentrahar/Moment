package com.homindolentrahar.moment.features.auth.data.mapper

import com.homindolentrahar.moment.features.auth.data.remote.dto.AuthUserDto
import com.homindolentrahar.moment.features.auth.data.remote.dto.UserDto
import com.homindolentrahar.moment.features.auth.domain.model.AuthUser
import java.time.LocalDateTime
import java.time.ZoneOffset

fun AuthUserDto.toAuthUser(): AuthUser {
    return AuthUser(
        uid = uid,
        name = name ?: "No Name",
        email = email ?: "No Email",
        phone = phone ?: "No Phone",
        createdAt = LocalDateTime.ofEpochSecond(createdAt, 0, ZoneOffset.UTC),
        lastSignedInAt = LocalDateTime.ofEpochSecond(lastSignedInAt, 0, ZoneOffset.UTC)
    )
}

fun UserDto.toDocumentSnapshot(): Map<String, Any> {
    return hashMapOf(
        "id" to id,
        "name" to name,
        "bio" to bio,
        "email" to email,
        "phone" to phone,
        "avatar_url" to avatarUrl,
        "created_at" to createdAt,
        "updated_at" to updatedAt
    )
}



