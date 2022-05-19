package com.homindolentrahar.moment.features.transaction.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.transaction.data.mapper.toDocumentSnapshot
import com.homindolentrahar.moment.features.transaction.data.mapper.toTransaction
import com.homindolentrahar.moment.features.transaction.data.remote.dto.TransactionDto
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : TransactionRepository {
    override suspend fun getAllTransactions(): Flow<Resource<List<Transaction>>> = flow {
        try {
            emit(Resource.Loading())

            val currentUser = auth.currentUser!!
            val querySnapshot = firestore
                .document(currentUser.uid)
                .collection(TransactionDto.COLLECTION)
                .get()
                .await()
            val transactions = querySnapshot.toObjects(TransactionDto::class.java)

            emit(Resource.Success(transactions.map { it.toTransaction() }))
        } catch (exception: Exception) {
            emit(Resource.Error(exception.localizedMessage ?: "Unexpected error"))
        }
    }

    override suspend fun getSingleTransaction(id: String): Flow<Resource<Transaction>> = flow {
        try {
            emit(Resource.Loading())

            val currentUser = auth.currentUser!!
            val documentSnapshot = firestore
                .document(currentUser.uid)
                .collection(TransactionDto.COLLECTION)
                .document(id)
                .get()
                .await()
            val transaction = documentSnapshot.toObject(TransactionDto::class.java)

            emit(Resource.Success(transaction?.toTransaction()))
        } catch (exception: Exception) {
            emit(Resource.Error(exception.localizedMessage ?: "Unexpected error"))
        }
    }

    override suspend fun addTransaction(transaction: Transaction): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

            val currentUser = auth.currentUser!!
            val transactionDto = TransactionDto.fromTransaction(transaction)

            firestore
                .document(currentUser.uid)
                .collection(TransactionDto.COLLECTION)
                .document(transactionDto.id)
                .set(transactionDto.toDocumentSnapshot())
                .await()

            emit(Resource.Success(Unit))
        } catch (exception: Exception) {
            emit(Resource.Error(exception.localizedMessage ?: "Unexpected error"))
        }
    }

    override suspend fun editTransaction(
        id: String,
        transaction: Transaction
    ): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

            val currentUser = auth.currentUser!!
            val transactionDto = TransactionDto.fromTransaction(transaction)

            firestore
                .document(currentUser.uid)
                .collection(TransactionDto.COLLECTION)
                .document(id)
                .update(transactionDto.toDocumentSnapshot())
                .await()

            emit(Resource.Success(Unit))
        } catch (exception: Exception) {
            emit(Resource.Error(exception.localizedMessage ?: "Unexpected error"))
        }
    }

    override suspend fun deleteTransaction(id: String): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

            val currentUser = auth.currentUser!!

            firestore
                .document(currentUser.uid)
                .collection(TransactionDto.COLLECTION)
                .document(id)
                .delete()
                .await()

            emit(Resource.Success(Unit))
        } catch (exception: Exception) {
            emit(Resource.Error(exception.localizedMessage ?: "Unexpected error"))
        }
    }
}