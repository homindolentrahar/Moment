package com.homindolentrahar.moment.features.transaction.domain.usecase

import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.model.TransactionType
import com.homindolentrahar.moment.features.transaction.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class ListenTransactionList @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(
        type: TransactionType,
        category: String,
        date: Date,
//        query: String,
    ): Flow<List<Transaction>> {
        return repository.listenAllTransactions()
            .map { list -> list.filter { if (type == TransactionType.ALL) true else it.type == type } }
            .map { list -> list.filter { if (category.isEmpty()) true else it.category == category } }
            .map { list -> list.filter { it.createdAt.month == date.month } }
//            .map { list -> list.filter { it.name.contains(query) } }
    }

}