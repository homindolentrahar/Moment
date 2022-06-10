package com.homindolentrahar.moment.features.bill.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.homindolentrahar.moment.features.bill.data.mapper.toBill
import com.homindolentrahar.moment.features.bill.data.mapper.toDocumentSnapshot
import com.homindolentrahar.moment.features.bill.data.remote.dto.BillDto
import com.homindolentrahar.moment.features.bill.domain.model.Bill
import com.homindolentrahar.moment.features.bill.domain.repository.BillRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BillRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : BillRepository {
    override suspend fun getAllBills(
    ): List<Bill> {
        val currentUser = auth.currentUser!!
        val querySnapshot = firestore
            .document(currentUser.uid)
            .collection(BillDto.COLLECTION)
            .get()
            .await()
        val bills = querySnapshot.toObjects(BillDto::class.java)

        return bills.map { it.toBill() }
    }

    override suspend fun getSingleBill(id: String): Bill? {
        val currentUser = auth.currentUser!!
        val documentSnapshot = firestore
            .document(currentUser.uid)
            .collection(BillDto.COLLECTION)
            .document(id)
            .get()
            .await()
        val bill = documentSnapshot.toObject(BillDto::class.java)

        return bill?.toBill()
    }

    override suspend fun saveBill(bill: Bill) {
        val currentUser = auth.currentUser!!
        val billDto = BillDto.fromBill(bill)

        firestore
            .document(currentUser.uid)
            .collection(BillDto.COLLECTION)
            .document(bill.id)
            .set(billDto.toDocumentSnapshot())
            .await()
    }

    override suspend fun updateBill(id: String, bill: Bill) {
        val currentUser = auth.currentUser!!
        val billDto = BillDto.fromBill(bill)

        firestore
            .document(currentUser.uid)
            .collection(BillDto.COLLECTION)
            .document(id)
            .update(billDto.toDocumentSnapshot())
            .await()
    }

    override suspend fun removeBill(id: String) {
        val currentUser = auth.currentUser!!

        firestore
            .document(currentUser.uid)
            .collection(BillDto.COLLECTION)
            .document(id)
            .delete()
            .await()
    }
}