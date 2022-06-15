package com.homindolentrahar.moment.features.bill.domain.usecase

import com.homindolentrahar.moment.features.bill.domain.model.Bill
import com.homindolentrahar.moment.features.bill.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetDueBills @Inject constructor(
    private val repository: BillRepository
) {
    suspend operator fun invoke(date: LocalDateTime): Flow<List<Bill>> = flow {
        val dueBills = repository.getAllBills()
            .filter { bill -> bill.timestamp.month == date.month }
            .filter { bill -> LocalDateTime.now().isBefore(bill.timestamp) }

        emit(dueBills)
    }
}