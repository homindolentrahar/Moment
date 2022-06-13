package com.homindolentrahar.moment.features.transaction.domain.usecase

import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteTransaction @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(id: String): Flow<Unit> = flow {
        emit(repository.deleteTransaction(id))
    }
}