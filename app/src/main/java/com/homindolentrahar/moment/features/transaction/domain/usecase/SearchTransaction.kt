package com.homindolentrahar.moment.features.transaction.domain.usecase

import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchTransaction @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(query: String): Flow<List<Transaction>> = flow {
        val result = repository.getAllTransactions()
            .filter { it.name.contains(query) }

        emit(result)
    }
}