package com.homindolentrahar.moment.features.bill.domain.usecase

import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.bill.domain.model.Bill
import com.homindolentrahar.moment.features.bill.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetDueBills @Inject constructor(
    private val repository: BillRepository
) {
    suspend operator fun invoke(): Flow<List<Bill>> = flow {
        val dueBills = repository.getAllBills()
            .filter { bill -> LocalDateTime.now().isBefore(bill.timestamp) }

        emit(dueBills)
    }
}