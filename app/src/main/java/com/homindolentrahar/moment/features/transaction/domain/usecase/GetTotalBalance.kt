package com.homindolentrahar.moment.features.transaction.domain.usecase

import com.homindolentrahar.moment.features.transaction.domain.model.TransactionType
import com.homindolentrahar.moment.features.transaction.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.math.abs

class GetTotalBalance @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(date: LocalDateTime = LocalDateTime.now()): Flow<Double> = flow {
        val expenses = repository.getAllTransactions()
            .filter { it.type == TransactionType.EXPENSE }
            .filter { it.timestamp.month == date.month }
            .sumOf { it.amount }
        val incomes = repository.getAllTransactions()
            .filter { it.type == TransactionType.INCOME }
            .filter { it.timestamp.month == date.month }
            .sumOf { it.amount }
        val balance = abs(incomes - expenses)

        emit(balance)
    }
}