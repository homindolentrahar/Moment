package com.homindolentrahar.moment.features.auth.domain.model

import java.time.LocalDateTime

data class AuthUser(
    val uid: String,
    val name: String,
    val email: String,
    val phone: String,
    val createdAt: LocalDateTime,
    val lastSignedInAt: LocalDateTime
)