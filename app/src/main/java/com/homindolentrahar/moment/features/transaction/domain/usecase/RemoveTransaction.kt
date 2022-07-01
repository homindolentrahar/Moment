package com.homindolentrahar.moment.features.transaction.domain.usecase

import com.google.android.gms.common.api.ApiException
import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoveTransaction @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(id: String): Flow<Unit> = flow {
        try {
            emit(repository.deleteTransaction(id))
        } catch (exception: ApiException) {
            throw exception
        }
    }
}