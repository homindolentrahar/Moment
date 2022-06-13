package com.homindolentrahar.moment.features.transaction.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.homindolentrahar.moment.features.transaction.data.mapper.toDocumentSnapshot
import com.homindolentrahar.moment.features.transaction.data.mapper.toTransaction
import com.homindolentrahar.moment.features.transaction.data.remote.dto.TransactionDto
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.repository.TransactionRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : TransactionRepository {
    override suspend fun getAllTransactions(): List<Transaction> {
        val currentUser = auth.currentUser!!
        val querySnapshot = firestore
            .document(currentUser.uid)
            .collection(TransactionDto.COLLECTION)
            .get()
            .await()
        val transactions = querySnapshot.toObjects(TransactionDto::class.java)

        return transactions.map { it.toTransaction() }
    }

    override suspend fun getSingleTransaction(id: String): Transaction? {
        val currentUser = auth.currentUser!!
        val documentSnapshot = firestore
            .document(currentUser.uid)
            .collection(TransactionDto.COLLECTION)
            .document(id)
            .get()
            .await()
        val transaction = documentSnapshot.toObject(TransactionDto::class.java)

        return transaction?.toTransaction()
    }

    override suspend fun addTransaction(transaction: Transaction) {
        val currentUser = auth.currentUser!!
        val transactionDto = TransactionDto.fromTransaction(transaction)

        firestore
            .document(currentUser.uid)
            .collection(TransactionDto.COLLECTION)
            .document(transactionDto.id)
            .set(transactionDto.toDocumentSnapshot())
            .await()
    }

    override suspend fun editTransaction(
        id: String,
        transaction: Transaction
    ) {
        val currentUser = auth.currentUser!!
        val transactionDto = TransactionDto.fromTransaction(transaction)

        firestore
            .document(currentUser.uid)
            .collection(TransactionDto.COLLECTION)
            .document(id)
            .update(transactionDto.toDocumentSnapshot())
            .await()
    }

    override suspend fun deleteTransaction(id: String) {
        val currentUser = auth.currentUser!!

        firestore
            .document(currentUser.uid)
            .collection(TransactionDto.COLLECTION)
            .document(id)
            .delete()
            .await()
    }
}