package com.homindolentrahar.moment.features.transaction.presentation.transaction_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class TransactionHomeData(
    val transactions: List<Transaction> = emptyList(),
    val expense: List<Transaction> = emptyList(),
    val income: List<Transaction> = emptyList()
)

@HiltViewModel
class TransactionHomeViewModel @Inject constructor(
    private val getMonthlyTransactions: GetMonthlyTransactions,
    private val listenMonthlyTransactions: ListenMonthlyTransactions,
    private val getExpenses: GetExpenses,
    private val getIncome: GetIncome,
) : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<TransactionHomeData>>(Resource.Initial())
    val uiState: StateFlow<Resource<TransactionHomeData>>
        get() = _uiState

    private val _state = MutableStateFlow(TransactionHomeState())
    val state: StateFlow<TransactionHomeState>
        get() = _state

    init {
//        allTransactions()
        listenTransactions()
    }

    fun listenTransactions(date: Date = Calendar.getInstance().time) {
        viewModelScope.launch {
            listenMonthlyTransactions(date)
                .onStart {
//                    _state.value = _state.value.copy(
//                        loading = true
//                    )
                    _uiState.value = Resource.Loading()
                }
                .zip(getExpenses(date)) { monthly, expenses ->
                    hashMapOf(
                        "monthly" to monthly,
                        "expenses" to expenses,
                    )
                }
                .zip(getIncome(date)) { pair, income ->
                    hashMapOf(
                        "income" to income,
                        pair.map { it.toPair() }.first(),
                        pair.map { it.toPair() }.last(),
                    )
                }
                .catch { error ->
//                    _state.value = _state.value.copy(
//                        error = error.localizedMessage?.toString() ?: "Unexpected error",
//                        loading = false
//                    )
                    _uiState.value = Resource.Error(error.localizedMessage ?: "Unexpected error")
                }
                .collect { data ->
//                    _state.value = _state.value.copy(
//                        error = "",
//                        transactions = data["monthly"] ?: emptyList(),
//                        expenses = data["expenses"] ?: emptyList(),
//                        income = data["income"] ?: emptyList(),
//                        loading = false,
//                    )
                    _uiState.value = Resource.Success(
                        TransactionHomeData(
                            transactions = data["monthly"] ?: emptyList(),
                            expense = data["expenses"] ?: emptyList(),
                            income = data["income"] ?: emptyList()
                        )
                    )
                }
        }
    }

    fun allTransactions(date: Date = Calendar.getInstance().time) {
        viewModelScope.launch {
            getMonthlyTransactions(date)
                .onStart {
                    _state.value = _state.value.copy(
                        loading = true
                    )
                }
                .zip(getExpenses(date)) { monthly, expenses ->
                    hashMapOf(
                        "monthly" to monthly,
                        "expenses" to expenses,
                    )
                }
                .zip(getIncome(date)) { pair, income ->
                    hashMapOf(
                        "income" to income,
                        pair.map { it.toPair() }.first(),
                        pair.map { it.toPair() }.last(),
                    )
                }
                .catch { error ->
                    error.printStackTrace()

                    _state.value = _state.value.copy(
                        error = error.localizedMessage?.toString() ?: "Unexpected error",
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
}