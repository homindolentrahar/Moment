package com.homindolentrahar.moment.features.transaction.data.remote.dto

import com.homindolentrahar.moment.features.transaction.domain.model.TransactionCategory
import java.time.ZoneOffset

data class TransactionCategoryDto(
    val id: String,
    val name: String,
    val slug: String,
    val icon: String,
    val timestamp: Long
) {
    companion object {
        fun fromTransactionCategory(category: TransactionCategory): TransactionCategoryDto {
            return TransactionCategoryDto(
                id = category.id,
                name = category.name,
                slug = category.slug,
                icon = category.icon,
                timestamp = category.timestamp.toEpochSecond(ZoneOffset.UTC)
            )
        }

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
