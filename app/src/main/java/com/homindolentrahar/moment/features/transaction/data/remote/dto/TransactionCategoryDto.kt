package com.homindolentrahar.moment.features.transaction.data.remote.dto

data class TransactionCategoryDto(
    val id: String,
    val name: String,
    val slug: String,
    val icon: String,
    val timestamp: Long
) {
    companion object {
        fun fromDocumentSnapshot(data: Map<String, Any>): TransactionCategoryDto {
            return TransactionCategoryDto(
                id = data["id"] as String,
                name = data["name"] as String,
                slug = data["slug"] as String,
                icon = data["icon"] as String,
                timestamp = data["timestamp"] as Long,
            )
        }
    }
}
