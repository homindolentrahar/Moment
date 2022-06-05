package com.homindolentrahar.moment.features.bill.data.remote.dto

import com.homindolentrahar.moment.features.bill.data.mapper.toDocumentSnapshot
import com.homindolentrahar.moment.features.bill.domain.model.Bill
import java.time.ZoneOffset

data class BillDto(
    val id: String,
    val name: String,
    val category: Map<String, Any>,
    val due: Long,
    val period: Map<String, Any>,
    val amount: Double,
    val status: Map<String, Any>,
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
                period = BillPeriodDto.fromBillPeriod(bill.period).toDocumentSnapshot(),
                amount = bill.amount,
                status = BillStatusDto.fromBillStatus(bill.status).toDocumentSnapshot(),
                timestamp = bill.timestamp.toEpochSecond(ZoneOffset.UTC)
            )
        }
    }
}
