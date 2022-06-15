package com.homindolentrahar.moment.features.bill.domain.usecase

import com.homindolentrahar.moment.features.bill.domain.model.Bill
import com.homindolentrahar.moment.features.bill.domain.model.BillStatus
import com.homindolentrahar.moment.features.bill.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetBillList @Inject constructor(
    private val repository: BillRepository
) {

    suspend operator fun invoke(
        query: String,
        status: BillStatus,
        categoryId: String,
        date: LocalDateTime
    ): Flow<List<Bill>> = flow {
        val filteredBills = repository.getAllBills()
            .filter { it.timestamp.month == date.month }
            .filter { it.name.contains(query) }
            .filter { it.status == status }
            .filter { if (categoryId.isEmpty()) true else it.category.id == categoryId }

        emit(filteredBills)
    }
}