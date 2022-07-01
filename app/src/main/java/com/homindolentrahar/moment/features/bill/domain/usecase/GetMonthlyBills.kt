package com.homindolentrahar.moment.features.bill.domain.usecase

import com.google.android.gms.common.api.ApiException
import com.homindolentrahar.moment.features.bill.domain.model.Bill
import com.homindolentrahar.moment.features.bill.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetMonthlyBills @Inject constructor(
    private val repository: BillRepository
) {
    suspend operator fun invoke(date: LocalDateTime): Flow<List<Bill>> =
        flow {
            try {
                val filteredBills = repository.getAllBills()
                    .filter { bill -> bill.timestamp.month == date.month }

                emit(filteredBills)
            } catch (exception: ApiException) {
                throw exception
            }
        }
}