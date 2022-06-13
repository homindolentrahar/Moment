package com.homindolentrahar.moment.features.transaction.domain.repository

import com.homindolentrahar.moment.features.transaction.domain.model.Transaction

interface TransactionRepository {
    suspend fun getAllTransactions(): List<Transaction>

    suspend fun getSingleTransaction(id: String): Transaction?

    suspend fun addTransaction(transaction: Transaction)

    suspend fun editTransaction(id: String, transaction: Transaction)

    suspend fun deleteTransaction(id: String)
}