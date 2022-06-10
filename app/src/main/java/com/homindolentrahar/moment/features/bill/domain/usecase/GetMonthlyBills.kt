package com.homindolentrahar.moment.features.bill.domain.usecase

import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.bill.domain.model.Bill
import com.homindolentrahar.moment.features.bill.domain.repository.BillRepository
import java.time.LocalDateTime
import javax.inject.Inject

class GetMonthlyBills @Inject constructor(
    private val repository: BillRepository
) {
    suspend operator fun invoke(date: LocalDateTime): Resource<List<Bill>> = try {
        val filteredBills = repository.getAllBills()
            .filter { bill -> bill.timestamp.month == date.month }

        Resource.Success(filteredBills)
    } catch (exception: Exception) {
        Resource.Error(exception.localizedMessage ?: "Unexpected error")
    }
}