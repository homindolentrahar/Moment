package com.homindolentrahar.moment.features.bill.domain.usecase

import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.bill.domain.model.Bill
import com.homindolentrahar.moment.features.bill.domain.repository.BillRepository
import javax.inject.Inject

class GetSingleBill @Inject constructor(
    private val repository: BillRepository
) {
    suspend operator fun invoke(billId: String): Resource<Bill> = try {
        val bill = repository.getSingleBill(billId)

        Resource.Success(bill)
    } catch (exception: Exception) {
        Resource.Error(exception.localizedMessage ?: "Unexpected error")
    }
}