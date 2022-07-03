package com.homindolentrahar.moment.features.transaction.presentation.transaction_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.model.TransactionType
import com.homindolentrahar.moment.features.transaction.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    private val listenTransactionList: ListenTransactionList
) : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<List<Transaction>>>(Resource.Initial())
    val uiState: StateFlow<Resource<List<Transaction>>>
        get() = _uiState

    private val _state = MutableStateFlow(TransactionListState())
    val state: StateFlow<TransactionListState>
        get() = _state

    init {
        listenTransactions()
    }

    fun listenTransactions(
        type: TransactionType = TransactionType.ALL,
        categoryId: String = "",
        date: Date = Calendar.getInstance().time,
    ) {
        viewModelScope.launch {
            listenTransactionList(type, categoryId, date)
                .onStart {
                    _uiState.value = Resource.Loading()
                }
                .catch { error ->
                    _uiState.value = Resource.Error(error.localizedMessage ?: "Unexpected error")
                }
                .collect { transactions ->
                    _uiState.value = Resource.Success(transactions)
                }
        }
    }
}