package com.homindolentrahar.moment.features.transaction.presentation.transaction_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.features.transaction.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    private val getAllTransactions: GetAllTransactions,
    private val getTransactionsByCategory: GetTransactionsByCategory,
    private val searchTransaction: SearchTransaction,
    private val getExpenses: GetExpenses,
    private val getIncome: GetIncome,
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionListState())
    val state: StateFlow<TransactionListState>
        get() = _state

    init {
        allTransactions()
    }

    private fun allTransactions() {
        viewModelScope.launch {
            getAllTransactions()
                .onStart {
                    _state.value = _state.value.copy(
                        loading = true
                    )
                }
                .catch { error ->
                    _state.value = _state.value.copy(
                        error = error.localizedMessage!!.toString(),
                        loading = false
                    )
                }
                .collect { transactions ->
                    _state.value = _state.value.copy(
                        error = "",
                        transactions = transactions,
                        loading = false,
                    )
                }
        }
    }

    fun byCategory(id: String) {
        viewModelScope.launch {
            getTransactionsByCategory(id)
                .onStart {
                    _state.value = _state.value.copy(
                        loading = true
                    )
                }
                .catch { error ->
                    _state.value = _state.value.copy(
                        error = error.localizedMessage!!.toString(),
                        loading = false
                    )
                }
                .collect { transactions ->
                    _state.value = _state.value.copy(
                        error = "",
                        transactions = transactions,
                        loading = false,
                    )
                }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            searchTransaction(query)
                .debounce(300)
                .onStart {
                    _state.value = _state.value.copy(
                        loading = true
                    )
                }
                .catch { error ->
                    _state.value = _state.value.copy(
                        error = error.localizedMessage!!.toString(),
                        loading = false
                    )
                }
                .collect { transactions ->
                    _state.value = _state.value.copy(
                        error = "",
                        transactions = transactions,
                        loading = false,
                    )
                }
        }
    }

    fun expenses() {
        viewModelScope.launch {
            getExpenses()
                .onStart {
                    _state.value = _state.value.copy(
                        loading = true
                    )
                }
                .catch { error ->
                    _state.value = _state.value.copy(
                        error = error.localizedMessage!!.toString(),
                        loading = false
                    )
                }
                .collect { transactions ->
                    _state.value = _state.value.copy(
                        error = "",
                        transactions = transactions,
                        loading = false,
                    )
                }
        }
    }

    fun income() {
        viewModelScope.launch {
            getIncome()
                .onStart {
                    _state.value = _state.value.copy(
                        loading = true
                    )
                }
                .catch { error ->
                    _state.value = _state.value.copy(
                        error = error.localizedMessage!!.toString(),
                        loading = false
                    )
                }
                .collect { transactions ->
                    _state.value = _state.value.copy(
                        error = "",
                        transactions = transactions,
                        loading = false,
                    )
                }
        }
    }
}