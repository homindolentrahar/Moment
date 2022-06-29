package com.homindolentrahar.moment.features.transaction.domain.model

import java.util.*

enum class TransactionType(name: String) {
    ALL("all"),
    INCOME("income"),
    EXPENSE("expense")
}

data class Transaction(
    var id: String = "",
    val name: String,
    val desc: String,
    val type: TransactionType,
    val amount: Double,
    val category: String,
    val timestamp: Date
)
