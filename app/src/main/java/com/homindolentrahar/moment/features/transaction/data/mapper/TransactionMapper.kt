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

fun TransactionDto.toTransaction(): Transaction {
    return Transaction(
        id = id,
        name = name,
        desc = desc,
        type = TransactionType.valueOf(type),
        category = TransactionCategoryDto.fromDocumentSnapshot(category).toTransactionCategory(),
        account = TransactionAccountDto.fromDocumentSnapshot(account).toTransactionAccount(),
        timestamp = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC)
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

fun TransactionAccountDto.toTransactionAccount(): TransactionAccount {
    return TransactionAccount(
        id = id,
        name = name,
        icon = icon,
        timestamp = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC)
    )
}