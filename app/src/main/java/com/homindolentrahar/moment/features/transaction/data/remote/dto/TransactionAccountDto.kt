package com.homindolentrahar.moment.features.transaction.data.remote.dto

import com.homindolentrahar.moment.features.transaction.domain.model.TransactionAccount
import java.time.ZoneOffset

data class TransactionAccountDto(
    val id: String,
    val name: String,
    val icon: String,
    val timestamp: Long
) {
    companion object {
        fun fromTransactionAccount(account: TransactionAccount): TransactionAccountDto {
            return TransactionAccountDto(
                id = account.id,
                name = account.name,
                icon = account.icon,
                timestamp = account.timestamp.toEpochSecond(ZoneOffset.UTC)
            )
        }

        fun fromDocumentSnapshot(data: Map<String, Any>): TransactionAccountDto {
            return TransactionAccountDto(
                id = data["id"] as String,
                name = data["name"] as String,
                icon = data["icon"] as String,
                timestamp = data["timestamp"] as Long
            )
        }
    }
}
