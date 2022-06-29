package com.homindolentrahar.moment.features.transaction.domain.usecase

import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.model.TransactionType
import com.homindolentrahar.moment.features.transaction.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

class GetExpenses @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(date: Date = Date()): Flow<List<Transaction>> =
        flow {
            val expenses = repository.getAllTransactions()
                .filter { it.type == TransactionType.EXPENSE }
                .filter { it.timestamp.month == date.month }

            emit(expenses)
        }
}