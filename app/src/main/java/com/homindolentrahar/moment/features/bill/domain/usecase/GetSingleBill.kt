package com.homindolentrahar.moment.features.bill.domain.usecase

import com.homindolentrahar.moment.features.bill.domain.model.Bill
import com.homindolentrahar.moment.features.bill.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSingleBill @Inject constructor(
    private val repository: BillRepository
) {
    suspend operator fun invoke(billId: String): Flow<Bill?> = flow {
        emit(repository.getSingleBill(billId))
    }
}