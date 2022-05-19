package com.homindolentrahar.moment.features.transaction.presentation.transaction_list

import com.homindolentrahar.moment.features.transaction.domain.model.Transaction

data class TransactionListState(
    val transactions: List<Transaction> = emptyList(),
    val loading: Boolean = false,
    val error: String = ""
)