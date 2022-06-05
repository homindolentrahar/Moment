package com.homindolentrahar.moment.features.bill.domain.repository

import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.bill.domain.model.Bill
import kotlinx.coroutines.flow.Flow

interface BillRepository {
    suspend fun getAllBills(): Flow<Resource<List<Bill>>>

    suspend fun getSingleBill(id: String): Flow<Resource<Bill>>

    suspend fun saveBill(bill: Bill): Flow<Resource<Unit>>

    suspend fun updateBill(id: String, bill: Bill): Flow<Resource<Unit>>

    suspend fun removeBill(id: String): Flow<Resource<Unit>>
}