package com.homindolentrahar.moment.features.bill.data.mapper

import com.homindolentrahar.moment.features.bill.data.remote.dto.BillCategoryDto
import com.homindolentrahar.moment.features.bill.data.remote.dto.BillPeriodDto
import com.homindolentrahar.moment.features.bill.data.remote.dto.BillStatusDto

fun BillCategoryDto.toDocumentSnapshot(): Map<String, Any> = hashMapOf(
    "id" to id,
    "name" to name,
    "slug" to slug,
    "icon" to icon,
    "timestamp" to timestamp
)

fun BillPeriodDto.toDocumentSnapshot(): Map<String, Any> = hashMapOf(
    "id" to id,
    "name" to name,
    "slug" to slug,
)

fun BillStatusDto.toDocumentSnapshot(): Map<String, Any> = hashMapOf(
    "id" to id,
    "name" to name,
    "slug" to slug
)