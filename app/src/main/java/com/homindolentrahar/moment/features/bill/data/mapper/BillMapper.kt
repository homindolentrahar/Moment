package com.homindolentrahar.moment.features.bill.data.mapper

import com.homindolentrahar.moment.features.bill.data.remote.dto.BillCategoryDto
import com.homindolentrahar.moment.features.bill.data.remote.dto.BillDto
import com.homindolentrahar.moment.features.bill.domain.model.Bill
import com.homindolentrahar.moment.features.bill.domain.model.BillCategory
import com.homindolentrahar.moment.features.bill.domain.model.BillPeriod
import com.homindolentrahar.moment.features.bill.domain.model.BillStatus
import java.time.LocalDateTime
import java.time.ZoneOffset

fun BillDto.toBill(): Bill = Bill(
    id = id,
    name = name,
    category = BillCategoryDto.fromDocumentSnapshot(category).toBillCategory(),
    due = LocalDateTime.ofEpochSecond(due, 0, ZoneOffset.UTC),
    period = BillPeriod.valueOf(period),
    amount = amount,
    status = BillStatus.valueOf(status),
    timestamp = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC)
)

fun BillDto.toDocumentSnapshot(): Map<String, Any> = hashMapOf(
    "id" to id,
    "name" to name,
    "category" to category,
    "due" to due,
    "period" to period,
    "amount" to amount,
    "status" to status,
    "timestamp" to timestamp
)

fun BillCategoryDto.toDocumentSnapshot(): Map<String, Any> = hashMapOf(
    "id" to id,
    "name" to name,
    "slug" to slug,
    "icon" to icon,
    "timestamp" to timestamp
)

fun BillCategoryDto.toBillCategory(): BillCategory = BillCategory(
    id = id,
    name = name,
    slug = slug,
    icon = icon,
    timestamp = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC)
)