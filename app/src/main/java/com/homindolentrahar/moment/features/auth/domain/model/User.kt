package com.homindolentrahar.moment.features.auth.domain.model

import java.time.LocalDateTime

data class User(
    val id: String,
    val name: String,
    val bio: String,
    val email: String,
    val phone: String,
    val avatarUrl: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
