package com.homindolentrahar.moment.features.bill.domain.model

import java.time.LocalDateTime

data class BillCategory(
    val id: String,
    val name: String,
    val slug: String,
    val icon: String,
    val timestamp: LocalDateTime
)
