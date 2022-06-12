package com.homindolentrahar.moment.features.bill.domain.usecase

import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.bill.domain.model.Bill
import com.homindolentrahar.moment.features.bill.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateBill @Inject constructor(
    private val repository: BillRepository
) {
    suspend operator fun invoke(id: String, bill: Bill): Flow<Unit> = flow {
        emit(repository.updateBill(id, bill))
    }
}