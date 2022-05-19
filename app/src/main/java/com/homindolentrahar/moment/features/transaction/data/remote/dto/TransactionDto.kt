package com.homindolentrahar.moment.features.transaction.data.remote.dto

import com.homindolentrahar.moment.features.transaction.data.mapper.toDocumentSnapshot
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import java.time.ZoneOffset

data class TransactionDto(
    val id: String,
    val name: String,
    val desc: String,
    val type: String,
    val category: Map<String, Any>,
    val account: Map<String, Any>,
    val timestamp: Long
) {
    companion object {
        const val COLLECTION = "transactions"

        fun fromTransaction(transaction: Transaction): TransactionDto {
            return TransactionDto(
                id = transaction.id,
                name = transaction.name,
                desc = transaction.desc,
                type = transaction.type.name,
                category = TransactionCategoryDto.fromTransactionCategory(transaction.category)
                    .toDocumentSnapshot(),
                account = TransactionAccountDto.fromTransactionAccount(transaction.account)
                    .toDocumentSnapshot(),
                timestamp = transaction.timestamp.toEpochSecond(ZoneOffset.UTC)
            )
        }
    }
}
