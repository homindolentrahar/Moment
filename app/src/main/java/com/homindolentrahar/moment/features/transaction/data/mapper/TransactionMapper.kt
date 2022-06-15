package com.homindolentrahar.moment.features.transaction.data.mapper

import com.homindolentrahar.moment.features.transaction.data.remote.dto.TransactionAccountDto
import com.homindolentrahar.moment.features.transaction.data.remote.dto.TransactionCategoryDto
import com.homindolentrahar.moment.features.transaction.data.remote.dto.TransactionDto
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.model.TransactionAccount
import com.homindolentrahar.moment.features.transaction.domain.model.TransactionCategory
import com.homindolentrahar.moment.features.transaction.domain.model.TransactionType
import java.time.LocalDateTime
import java.time.ZoneOffset

fun TransactionDto.toDocumentSnapshot(): Map<String, Any> {
    return hashMapOf(
        "id" to id,
        "name" to name,
        "desc" to desc,
        "type" to type,
        "amount" to amount,
        "category" to category,
        "account" to account,
        "createdAt" to createdAt,
        "updatedAt" to updatedAt,
    )
}

fun TransactionDto.toTransaction(): Transaction {
    return Transaction(
        id = id,
        name = name,
        desc = desc,
        type = TransactionType.valueOf(type),
        amount = amount,
        category = TransactionCategoryDto.fromDocumentSnapshot(category).toTransactionCategory(),
        account = TransactionAccountDto.fromDocumentSnapshot(account).toTransactionAccount(),
        createdAt = LocalDateTime.ofEpochSecond(createdAt, 0, ZoneOffset.UTC),
        updatedAt = LocalDateTime.ofEpochSecond(updatedAt, 0, ZoneOffset.UTC)
    )
}

fun TransactionCategoryDto.toDocumentSnapshot(): Map<String, Any> {
    return hashMapOf(
        "id" to id,
        "name" to name,
        "icon" to icon,
        "slug" to slug,
        "timestamp" to timestamp
    )
}

fun TransactionCategoryDto.toTransactionCategory(): TransactionCategory {
    return TransactionCategory(
        id = id,
        name = name,
        icon = icon,
        slug = slug,
        timestamp = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC)
    )
}

fun TransactionAccountDto.toDocumentSnapshot(): Map<String, Any> {
    return hashMapOf(
        "id" to id,
        "name" to name,
        "icon" to icon,
        "timestamp" to timestamp
    )
}

fun TransactionAccountDto.toTransactionAccount(): TransactionAccount {
    return TransactionAccount(
        id = id,
        name = name,
        icon = icon,
        timestamp = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC)
    )
}