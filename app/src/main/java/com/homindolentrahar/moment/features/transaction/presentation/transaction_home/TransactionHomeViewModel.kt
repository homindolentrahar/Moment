package com.homindolentrahar.moment.features.transaction.presentation.transaction_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionHomeViewModel @Inject constructor(
    private val getMonthlyTransactions: GetMonthlyTransactions,
    private val saveTransaction: SaveTransaction,
    private val getExpenses: GetExpenses,
    private val getIncome: GetIncome
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionHomeState())
    val state: StateFlow<TransactionHomeState>
        get() = _state

    init {
        allTransactions()
    }

    fun allTransactions() {
        viewModelScope.launch {
            getMonthlyTransactions()
                .onStart {
                    _state.value = _state.value.copy(
                        loading = true
                    )
                }
                .zip(getExpenses()) { monthly, expenses ->
                    hashMapOf(
                        "monthly" to monthly,
                        "expenses" to expenses,
                    )
                }
                .zip(getIncome()) { pair, income ->
                    hashMapOf(
                        "income" to income,
                        pair.map { it.toPair() }.first(),
                        pair.map { it.toPair() }.last(),
                    )
                }
                .catch { error ->
                    _state.value = _state.value.copy(
                        error = error.localizedMessage!!.toString(),
                        loading = false
                    )
                }
                .collect { data ->
                    _state.value = _state.value.copy(
                        error = "",
                        transactions = data["monthly"] ?: emptyList(),
                        expenses = data["expenses"] ?: emptyList(),
                        income = data["income"] ?: emptyList(),
                        loading = false,
                    )
                }
        }
    }

    fun save(transaction: Transaction) {
        viewModelScope.launch {
            saveTransaction(transaction)
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
                .collect {
                    _state.value = _state.value.copy(
                        error = "",
                        loading = false
                    )
                }
        }
    }
}