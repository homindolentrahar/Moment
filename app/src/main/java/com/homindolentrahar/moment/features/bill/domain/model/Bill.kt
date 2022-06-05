package com.homindolentrahar.moment.features.bill.domain.model

import java.time.LocalDateTime

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
