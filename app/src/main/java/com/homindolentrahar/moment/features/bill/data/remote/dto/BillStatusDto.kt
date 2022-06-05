package com.homindolentrahar.moment.features.bill.data.remote.dto

import com.homindolentrahar.moment.features.bill.domain.model.BillStatus

data class BillStatusDto(
    val id: String,
    val name: String,
    val slug: String
) {
    companion object {
        fun fromDocumentSnapshot(data: Map<String, Any>): BillStatusDto {
            return BillStatusDto(
                id = data["id"] as String,
                name = data["name"] as String,
                slug = data["slug"] as String
            )
        }

        fun fromBillStatus(status: BillStatus): BillStatusDto {
            return BillStatusDto(
                id = status.id,
                name = status.name,
                slug = status.slug
            )
        }
    }
}