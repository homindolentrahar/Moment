package com.homindolentrahar.moment.features.bill.domain.model

import java.time.LocalDateTime

enum class BillStatus(name: String) {
    USING("using"),
    PAID("paid"),
    UNPAID("unpaid"),
}

enum class BillPeriod(name: String) {
    ONE_TIME("one_time"),
    ANNUAL("annual"),
    MONTHLY("monthly"),
    WEEKLY("weekly"),
    DAILY("daily"),
}

data class Bill(
    val id: String,
    val name: String,
    val category: BillCategory,
    val due: LocalDateTime,
    val period: BillPeriod,
    val amount: Double,
    val status: BillStatus,
    val timestamp: LocalDateTime
)
