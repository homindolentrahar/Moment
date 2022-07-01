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
    val createdAt: Timestamp,
    val updatedAt: Timestamp,
) {
    companion object {
        fun fromTransaction(transaction: Transaction): TransactionDto {
            return TransactionDto(
                id = transaction.id,
                name = transaction.name,
                desc = transaction.desc,
                type = transaction.type.value,
                amount = transaction.amount,
                category = transaction.category,
                createdAt = Timestamp(transaction.createdAt),
                updatedAt = Timestamp(transaction.updatedAt)
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
                createdAt = data["created_at"] as Timestamp,
                updatedAt = data["updated_at"] as Timestamp
            )
        }
    }
}
