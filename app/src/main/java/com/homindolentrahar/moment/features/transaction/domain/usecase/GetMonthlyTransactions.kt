package com.homindolentrahar.moment.features.transaction.domain.usecase

import android.util.Log
import com.google.android.gms.common.api.ApiException
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class GetMonthlyTransactions @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(date: Date): Flow<List<Transaction>> =
        flow {
            try {
                val monthlyTransactions = repository.getAllTransactions()
                    .filter { tr -> tr.createdAt.month == date.month }

                emit(monthlyTransactions)
            } catch (exception: ApiException) {
                Log.d(
                    GetMonthlyTransactions::class.java.simpleName,
                    exception.localizedMessage ?: "Unknown error"
                )
                throw exception
            }
        }
}