package com.homindolentrahar.moment.features.transaction.domain.repository

import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun getAllTransactions(): Flow<Resource<List<Transaction>>>

    suspend fun getSingleTransaction(id: String): Flow<Resource<Transaction>>

    suspend fun addTransaction(transaction: Transaction): Flow<Resource<Unit>>

    suspend fun editTransaction(id: String, transaction: Transaction): Flow<Resource<Unit>>

    suspend fun deleteTransaction(id: String): Flow<Resource<Unit>>
}