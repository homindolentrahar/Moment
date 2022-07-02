package com.homindolentrahar.moment.features.transaction.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.homindolentrahar.moment.core.util.Constants
import com.homindolentrahar.moment.features.transaction.data.mapper.toDocumentSnapshot
import com.homindolentrahar.moment.features.transaction.data.mapper.toTransaction
import com.homindolentrahar.moment.features.transaction.data.remote.dto.TransactionDto
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.repository.TransactionRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : TransactionRepository {
    override suspend fun listenAllTransactions(): Flow<List<Transaction>> = callbackFlow {
        firestore
            .collection(Constants.TRANSACTIONS_COLLECTION)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) close(exception)

                val transactions = snapshot?.documents?.map { document ->
                    TransactionDto.fromDocumentSnapshot(document.id, document.data!!)
                }?.map { dto -> dto.toTransaction() } ?: emptyList()

                trySend(transactions)
            }

        awaitClose()
    }

    override suspend fun getAllTransactions(): List<Transaction> {
//        val currentUser = auth.currentUser!!

        val querySnapshot = firestore
            .collection(Constants.TRANSACTIONS_COLLECTION)
//            .document(currentUser.uid)
//            .collection(Constants.DATA_COLLECTION)
            .get()
            .await()
        val transactions = querySnapshot.map { query ->
            TransactionDto.fromDocumentSnapshot(query.id, query.data)
        }

        return transactions.map { tr -> tr.toTransaction() }
    }

    override suspend fun getSingleTransaction(id: String): Transaction? {
//        val currentUser = auth.currentUser!!

        val documentSnapshot = firestore
            .collection(Constants.TRANSACTIONS_COLLECTION)
//            .document(currentUser.uid)
//            .collection(Constants.DATA_COLLECTION)
            .document(id)
            .get()
            .await()
        val transaction = documentSnapshot.toObject(TransactionDto::class.java)

        return transaction?.toTransaction()
    }

    override suspend fun addTransaction(transaction: Transaction) {
//        val currentUser = auth.currentUser!!
        val transactionDto = TransactionDto.fromTransaction(transaction)

        firestore
            .collection(Constants.TRANSACTIONS_COLLECTION)
//            .document(currentUser.uid)
//            .collection(Constants.DATA_COLLECTION)
            .add(transactionDto.toDocumentSnapshot())
            .await()
    }

    override suspend fun editTransaction(
        id: String,
        transaction: Transaction
    ) {
//        val currentUser = auth.currentUser!!
        val transactionDto = TransactionDto.fromTransaction(transaction)

        firestore
            .collection(Constants.TRANSACTIONS_COLLECTION)
//            .document(currentUser.uid)
//            .collection(Constants.DATA_COLLECTION)
            .document(id)
            .update(transactionDto.toDocumentSnapshot())
            .await()
    }

    override suspend fun deleteTransaction(id: String) {
//        val currentUser = auth.currentUser!!

        firestore
            .collection(Constants.TRANSACTIONS_COLLECTION)
//            .document(currentUser.uid)
//            .collection(Constants.DATA_COLLECTION)
            .document(id)
            .delete()
            .await()
    }
}