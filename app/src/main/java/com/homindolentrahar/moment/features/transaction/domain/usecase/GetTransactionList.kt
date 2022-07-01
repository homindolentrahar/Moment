package com.homindolentrahar.moment.features.transaction.domain.usecase

import com.google.android.gms.common.api.ApiException
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.model.TransactionType
import com.homindolentrahar.moment.features.transaction.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class GetTransactionList @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(
        type: TransactionType,
        category: String,
        date: Date,
//        query: String,
    ): Flow<List<Transaction>> = flow {
        try {
            val filteredTransactions = repository.getAllTransactions()
                .filter { if (type == TransactionType.ALL) true else it.type == type }
                .filter { if (category.isEmpty()) true else it.category == category }
                .filter { it.createdAt.month == date.month }
//                .filter { it.name.contains(query) }

            emit(filteredTransactions)
        } catch (exception: ApiException) {
            throw exception
        }
    }

}