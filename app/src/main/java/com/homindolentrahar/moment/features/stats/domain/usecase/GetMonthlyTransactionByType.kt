package com.homindolentrahar.moment.features.stats.domain.usecase

import com.homindolentrahar.moment.features.transaction.domain.model.TransactionType
import com.homindolentrahar.moment.features.transaction.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

class GetMonthlyTransactionByType @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(date: Date, type: TransactionType): Flow<Double> = flow {
        val result = repository.getAllTransactions()
            .filter { it.timestamp.month == date.month }
            .filter { it.type == type }
            .sumOf { it.amount }

        emit(result)
    }

}