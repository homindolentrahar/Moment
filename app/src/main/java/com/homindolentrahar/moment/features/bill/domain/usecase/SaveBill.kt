package com.homindolentrahar.moment.features.bill.domain.usecase

import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.bill.domain.model.Bill
import com.homindolentrahar.moment.features.bill.domain.repository.BillRepository
import javax.inject.Inject

class SaveBill @Inject constructor(
    private val repository: BillRepository
) {
    suspend operator fun invoke(bill: Bill): Resource<Unit> = try {
        Resource.Success(repository.saveBill(bill))
    } catch (exception: Exception) {
        Resource.Error(exception.localizedMessage ?: "Unexpected error")
    }
}