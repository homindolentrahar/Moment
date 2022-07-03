package com.homindolentrahar.moment.features.transaction.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

enum class TransactionType(val value: String) {
    ALL(""),
    INCOME("Income"),
    EXPENSE("Expense")
}

@Parcelize
data class Transaction(
    var id: String = "",
    val name: String,
    val desc: String,
    val type: TransactionType,
    val amount: Double,
    val category: String,
    val createdAt: Date,
    val updatedAt: Date
) : Parcelable
