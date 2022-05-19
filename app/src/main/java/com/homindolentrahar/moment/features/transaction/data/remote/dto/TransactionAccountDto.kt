package com.homindolentrahar.moment.features.transaction.data.remote.dto

data class TransactionAccountDto(
    val id: String,
    val name: String,
    val icon: String,
    val timestamp: Long
) {
    companion object {
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
