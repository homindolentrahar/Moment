package com.homindolentrahar.moment.features.transaction.domain.usecase

import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.model.TransactionType
import com.homindolentrahar.moment.features.transaction.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class GetExpenses @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(date: Date): Flow<List<Transaction>> =
        repository.listenAllTransactions()
            .map { list ->
                list.filter { it.type == TransactionType.EXPENSE }
                list.filter { it.createdAt.month == date.month }
            }
}