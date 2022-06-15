package com.homindolentrahar.moment.features.transaction.domain.usecase

import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.model.TransactionType
import com.homindolentrahar.moment.features.transaction.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetIncome @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(date: LocalDateTime = LocalDateTime.now()): Flow<List<Transaction>> =
        flow {
            val income = repository.getAllTransactions()
                .filter { it.type == TransactionType.INCOME }
                .filter { it.createdAt.month == date.month }

            emit(income)
        }
}