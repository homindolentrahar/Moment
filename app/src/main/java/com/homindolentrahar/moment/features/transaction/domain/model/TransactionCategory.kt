package com.homindolentrahar.moment.features.transaction.domain.model

import java.time.LocalDateTime

data class TransactionCategory(
    val id: String,
    val name: String,
    val slug: String,
    val icon: String,
    val timestamp: LocalDateTime
)
