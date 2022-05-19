package com.homindolentrahar.moment.features.transaction.presentation.transaction_detail

import com.homindolentrahar.moment.features.transaction.domain.model.Transaction

data class TransactionDetailState(
    val transaction: Transaction? = null,
    val loading: Boolean = false,
    val error: String = ""
)