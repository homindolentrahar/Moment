package com.homindolentrahar.moment.features.transaction.presentation.transaction_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.usecase.RemoveTransaction
import com.homindolentrahar.moment.features.transaction.domain.usecase.UpdateTransaction
import com.homindolentrahar.moment.features.transaction.domain.usecase.GetSingleTransaction
import com.homindolentrahar.moment.features.transaction.domain.usecase.SaveTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    private val getSingleTransaction: GetSingleTransaction,
    private val saveTransaction: SaveTransaction,
    private val updateTransaction: UpdateTransaction,
    private val removeTransaction: RemoveTransaction
) : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<Transaction>>(Resource.Initial())
    val uiState: StateFlow<Resource<Transaction>>
        get() = _uiState

    fun getTransaction(id: String) {
        Log.d(TransactionDetailViewModel::class.java.simpleName, "Get Transction called")
        viewModelScope.launch {
            getSingleTransaction(id)
                .onStart {
                    _uiState.value = Resource.Loading()
                }
                .catch { error ->
                    _uiState.value = Resource.Error(error.message ?: "Unexpected error")
                }
                .collect { transaction ->
                    _uiState.value = Resource.Success(transaction)
                }
        }
    }

    fun save(transaction: Transaction) {
        viewModelScope.launch {
            saveTransaction(transaction)
                .onStart {
                    _uiState.value = Resource.Loading()
                }
                .catch { error ->
                    _uiState.value = Resource.Error(error.message ?: "Unexpected error")
                }
                .collect {
                    _uiState.value = Resource.Success(null)
                }
        }
    }

    fun update(id: String, transaction: Transaction) {
        viewModelScope.launch {
            updateTransaction(id, transaction)
                .onStart {
                    _uiState.value = Resource.Loading()
                }
                .catch { error ->
                    _uiState.value = Resource.Error(error.message ?: "Unexpected error")
                }
                .collect {
                    _uiState.value = Resource.Success(null)
                }
        }
    }

    fun remove(id: String) {
        viewModelScope.launch {
            removeTransaction(id)
                .onStart {
                    _uiState.value = Resource.Loading()
                }
                .catch { error ->
                    _uiState.value = Resource.Error(error.message ?: "Unexpected error")
                }
                .collect {
                    _uiState.value = Resource.Success(null)
                }
        }
    }
}