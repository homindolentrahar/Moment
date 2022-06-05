package com.homindolentrahar.moment.features.bill.data.remote.dto

import com.homindolentrahar.moment.features.bill.domain.model.BillPeriod

data class BillPeriodDto(
    val id: String,
    val name: String,
    val slug: String
) {
    companion object {
        fun fromDocumentSnapshot(data: Map<String, Any>): BillPeriodDto {
            return BillPeriodDto(
                id = data["id"] as String,
                name = data["name"] as String,
                slug = data["slug"] as String,
            )
        }

        fun fromBillPeriod(period: BillPeriod): BillPeriodDto {
            return BillPeriodDto(
                id = period.id,
                name = period.name,
                slug = period.slug
            )
        }
    }
}
