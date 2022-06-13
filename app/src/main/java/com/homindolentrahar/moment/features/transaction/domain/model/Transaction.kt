package com.homindolentrahar.moment.features.transaction.domain.model

import java.time.LocalDateTime

enum class TransactionType(name: String) {
    INCOME("income"),
    EXPENSE("expense")
}

data class Transaction(
    val id: String,
    val name: String,
    val desc: String,
    val type: TransactionType,
    val amount: Double,
    val category: TransactionCategory,
    val account: TransactionAccount,
    val timestamp: LocalDateTime
)
