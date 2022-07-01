package com.homindolentrahar.moment.features.transaction.presentation.transaction_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.features.transaction.domain.model.TransactionType
import com.homindolentrahar.moment.features.transaction.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    private val getTransactionList: GetTransactionList,
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionListState())
    val state: StateFlow<TransactionListState>
        get() = _state

    init {
        transactionList()
    }

    fun transactionList(
        type: TransactionType = TransactionType.ALL,
        categoryId: String = "",
        date: Date = Calendar.getInstance().time,
    ) {
        viewModelScope.launch {
            getTransactionList(
                type,
                categoryId,
                date,
            )
                .onStart {
                    _state.value = _state.value.copy(
                        loading = true
                    )
                }
                .catch { error ->
                    _state.value = _state.value.copy(
                        error = error.localizedMessage?.toString() ?: "Unexpected error",
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