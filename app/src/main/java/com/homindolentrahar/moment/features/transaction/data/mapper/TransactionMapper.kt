package com.homindolentrahar.moment.features.transaction.data.mapper

import com.homindolentrahar.moment.features.transaction.data.remote.dto.TransactionDto
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.model.TransactionType

fun TransactionDto.toDocumentSnapshot(): Map<String, Any> {
    return hashMapOf(
        "name" to name,
        "desc" to desc,
        "type" to type,
        "amount" to amount,
        "category" to category,
        "timestamp" to timestamp
    )
}

fun TransactionDto.toTransaction(): Transaction {
    return Transaction(
        id = id,
        name = name,
        desc = desc,
        type = TransactionType.valueOf(type),
        amount = amount,
        category = category,
        timestamp = timestamp.toDate()
    )
}