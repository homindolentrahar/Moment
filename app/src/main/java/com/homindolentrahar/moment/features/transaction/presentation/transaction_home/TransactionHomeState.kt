package com.homindolentrahar.moment.features.transaction.presentation.transaction_home

import com.homindolentrahar.moment.features.transaction.domain.model.Transaction

data class TransactionHomeState(
    val transactions: List<Transaction> = emptyList(),
    val expenses: List<Transaction> = emptyList(),
    val income: List<Transaction> = emptyList(),
    val loading: Boolean = false,
    val error: String = ""
)