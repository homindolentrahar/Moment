package com.homindolentrahar.moment.features.transaction.data.remote.dto

import com.google.firebase.Timestamp
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction

data class TransactionDto(
    val id: String,
    val name: String,
    val desc: String,
    val type: String,
    val amount: Double,
    val category: String,
    val timestamp: Timestamp
) {
    companion object {
        const val COLLECTION = "transactions"

        fun fromTransaction(transaction: Transaction): TransactionDto {
            return TransactionDto(
                id = transaction.id,
                name = transaction.name,
                desc = transaction.desc,
                type = transaction.type.name,
                amount = transaction.amount,
                category = transaction.category,
                timestamp = Timestamp(transaction.timestamp)
            )
        }

        fun fromDocumentSnapshot(id: String, data: Map<String, Any>): TransactionDto {
            return TransactionDto(
                id = id,
                name = data["name"] as String,
                desc = data["desc"] as String,
                type = data["type"] as String,
                amount = data["amount"] as Double,
                category = data["category"] as String,
                timestamp = data["timestamp"] as Timestamp
            )
        }
    }
}
