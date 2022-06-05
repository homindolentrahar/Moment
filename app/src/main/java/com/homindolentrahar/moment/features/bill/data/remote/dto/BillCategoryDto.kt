package com.homindolentrahar.moment.features.bill.data.remote.dto

import com.homindolentrahar.moment.features.bill.domain.model.BillCategory
import java.time.ZoneOffset

data class BillCategoryDto(
    val id: String,
    val name: String,
    val slug: String,
    val icon: String,
    val timestamp: Long
) {
    companion object {
        fun fromDocumentSnapshot(data: Map<String, Any>): BillCategoryDto {
            return BillCategoryDto(
                id = data["id"] as String,
                name = data["name"] as String,
                slug = data["slug"] as String,
                icon = data["icon"] as String,
                timestamp = data["timestamp"] as Long,
            )
        }

        fun fromBillCategory(category: BillCategory): BillCategoryDto {
            return BillCategoryDto(
                id = category.id,
                name = category.name,
                slug = category.slug,
                icon = category.icon,
                timestamp = category.timestamp.toEpochSecond(ZoneOffset.UTC)
            )
        }
    }
}