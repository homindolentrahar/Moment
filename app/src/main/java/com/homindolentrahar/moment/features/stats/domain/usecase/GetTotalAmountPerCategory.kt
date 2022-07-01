package com.homindolentrahar.moment.features.stats.domain.usecase

import com.homindolentrahar.moment.features.transaction.domain.model.TransactionType
import com.homindolentrahar.moment.features.transaction.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

class GetTotalAmountPerCategory @Inject constructor(
    private val repository: TransactionRepository,
) {

    suspend operator fun invoke(
        date: Date,
        type: TransactionType
    ): Flow<List<Map<String, Any>>> =
        flow {
            val perCategory = repository.getAllTransactions()
                .filter { it.createdAt.month == date.month }
                .filter { it.type == type }
                .groupBy { it.category }
                .map { entry ->
                    mapOf(
                        "category" to entry.key,
                        "total" to entry.value.sumOf { it.amount }
                    )
                }


            emit(perCategory)
        }

}