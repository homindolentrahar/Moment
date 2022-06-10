package com.homindolentrahar.moment.features.bill.domain.usecase

import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.bill.domain.model.Bill
import com.homindolentrahar.moment.features.bill.domain.repository.BillRepository
import java.time.LocalDateTime
import javax.inject.Inject

class GetDueBills @Inject constructor(
    private val repository: BillRepository
) {
    suspend operator fun invoke(): Resource<List<Bill>> =
        try {
            val dueBills = repository.getAllBills()
                .filter { bill -> LocalDateTime.now().isBefore(bill.timestamp) }

            Resource.Success(dueBills)
        } catch (exception: Exception) {
            Resource.Error(exception.localizedMessage ?: "Unexpected error")
        }
}