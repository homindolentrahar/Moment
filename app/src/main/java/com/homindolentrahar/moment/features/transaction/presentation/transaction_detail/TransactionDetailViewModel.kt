package com.homindolentrahar.moment.features.transaction.presentation.transaction_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _uiState = MutableStateFlow<Resource<Unit>>(Resource.Initial())
    val uiState: StateFlow<Resource<Unit>>
        get() = _uiState

    private val _state = MutableLiveData<Resource<Unit>>(Resource.Initial())
    val state: LiveData<Resource<Unit>>
        get() = _state

    fun save(transaction: Transaction) {
        viewModelScope.launch {
            saveTransaction(transaction)
                .onStart {
                    _uiState.value = Resource.Loading()
                    _state.value = Resource.Loading()
                }
                .catch { error ->
                    _uiState.value = Resource.Error(error.message ?: "Unexpected error")
                    _state.value = Resource.Error(error.message ?: "Unexpected error")
                }
                .collect {
                    _uiState.value = Resource.Success(Unit)
                    _state.value = Resource.Success(Unit)
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
                    _uiState.value = Resource.Success(Unit)
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
                    _uiState.value = Resource.Success(Unit)
                }
        }
    }
}