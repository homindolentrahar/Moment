package com.homindolentrahar.moment.features.bill.domain.usecase

import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.bill.domain.model.Bill
import com.homindolentrahar.moment.features.bill.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllBills @Inject constructor(
    private val repository: BillRepository
) {
    suspend operator fun invoke(): Resource<List<Bill>> =
        try {
            val allBills = repository.getAllBills()

            Resource.Success(allBills)
        } catch (exception: Exception) {
            Resource.Error(exception.localizedMessage ?: "Unexpected error")
        }
}