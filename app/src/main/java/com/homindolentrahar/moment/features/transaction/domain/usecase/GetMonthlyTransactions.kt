package com.homindolentrahar.moment.features.transaction.domain.usecase

import com.homindolentrahar.moment.features.bill.domain.repository.BillRepository
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetMonthlyTransactions @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(date: LocalDateTime = LocalDateTime.now()): Flow<List<Transaction>> =
        flow {
            val monthlyTransactions = repository.getAllTransactions()
                .filter { tr -> tr.timestamp.month == date.month }

            emit(monthlyTransactions)
        }
}