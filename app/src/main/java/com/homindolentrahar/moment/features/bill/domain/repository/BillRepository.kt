package com.homindolentrahar.moment.features.bill.domain.repository

import com.homindolentrahar.moment.features.bill.domain.model.Bill

interface BillRepository {
    suspend fun getAllBills(): List<Bill>

    suspend fun getSingleBill(id: String): Bill?

    suspend fun saveBill(bill: Bill)

    suspend fun updateBill(id: String, bill: Bill)

    suspend fun removeBill(id: String)
}