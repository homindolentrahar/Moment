package com.homindolentrahar.moment.features.bill.data.remote.dto

import com.homindolentrahar.moment.features.bill.data.mapper.toDocumentSnapshot
import com.homindolentrahar.moment.features.bill.domain.model.Bill
import java.time.ZoneOffset

data class BillDto(
    val id: String,
    val name: String,
    val category: Map<String, Any>,
    val due: Long,
    val period: String,
    val amount: Double,
    val status: String,
    val timestamp: Long
) {
    companion object {
        const val COLLECTION = "bills"

        fun fromBill(bill: Bill): BillDto {
            return BillDto(
                id = bill.id,
                name = bill.name,
                category = BillCategoryDto.fromBillCategory(bill.category).toDocumentSnapshot(),
                due = bill.due.toEpochSecond(ZoneOffset.UTC),
                period = bill.period.name,
                amount = bill.amount,
                status = bill.status.name,
                timestamp = bill.timestamp.toEpochSecond(ZoneOffset.UTC)
            )
        }
    }
}
