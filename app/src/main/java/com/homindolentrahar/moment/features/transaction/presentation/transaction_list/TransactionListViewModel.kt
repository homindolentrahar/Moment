package com.homindolentrahar.moment.features.transaction.presentation.transaction_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.usecase.AddTransaction
import com.homindolentrahar.moment.features.transaction.domain.usecase.DeleteTransaction
import com.homindolentrahar.moment.features.transaction.domain.usecase.EditTransaction
import com.homindolentrahar.moment.features.transaction.domain.usecase.GetAllTransactions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    private val getAllTransactions: GetAllTransactions,
    private val addTransaction: AddTransaction,
    private val editTransaction: EditTransaction,
    private val deleteTransaction: DeleteTransaction
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionListState())
    val state: StateFlow<TransactionListState>
        get() = _state

    fun allTransactions() {
        viewModelScope.launch {
            getAllTransactions()
                .collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                transactions = emptyList(),
                                error = resource.message ?: "Unexpected error",
                                loading = false
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                loading = resource.isLoading
                            )
                        }
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                transactions = resource.data ?: emptyList(),
                                error = "",
                                loading = false
                            )
                        }
                    }
                }
        }
    }

    fun newTransaction(transaction: Transaction) {
        viewModelScope.launch {
            addTransaction(transaction)
                .collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                error = resource.message ?: "Unexpected error",
                                loading = false
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                loading = resource.isLoading
                            )
                        }
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                error = "",
                                loading = false
                            )
                        }
                    }
                }
        }
    }

    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch {
            editTransaction(transaction.id, transaction)
                .collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                error = resource.message ?: "Unexpected error",
                                loading = false
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                loading = resource.isLoading
                            )
                        }
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                error = "",
                                loading = false
                            )
                        }
                    }
                }
        }
    }

    fun removeTransaction(id: String) {
        viewModelScope.launch {
            deleteTransaction(id)
                .collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                error = resource.message ?: "Unexpected error",
                                loading = false
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                loading = resource.isLoading
                            )
                        }
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                error = "",
                                loading = false
                            )
                        }
                    }
                }
        }
    }

}