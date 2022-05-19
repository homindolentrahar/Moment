package com.homindolentrahar.moment.features.transaction.domain.model

import java.time.LocalDateTime

data class TransactionAccount(
    val id: String,
    val name: String,
    val icon: String,
    val timestamp: LocalDateTime
)
