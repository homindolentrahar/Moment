package com.homindolentrahar.moment.features.transaction.domain.usecase

import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.model.TransactionType
import com.homindolentrahar.moment.features.transaction.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetTransactionList @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(
        type: TransactionType = TransactionType.ALL,
        categoryId: String = "",
        date: LocalDateTime = LocalDateTime.now(),
        query: String = "",
    ): Flow<List<Transaction>> = flow {
        val filteredTransactions = repository.getAllTransactions()
            .filter { if (type == TransactionType.ALL) true else it.type == type }
            .filter { if (categoryId.isEmpty()) true else it.category.id == categoryId }
            .filter { it.createdAt.month == date.month }
            .filter { it.name.contains(query) }

        emit(filteredTransactions)
    }

}