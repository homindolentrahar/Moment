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

@HiltViewModel
class TransactionHomeViewModel @Inject constructor(
    private val listenMonthlyTransactions: ListenMonthlyTransactions,
) : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<List<Transaction>>>(Resource.Initial())
    val uiState: StateFlow<Resource<List<Transaction>>>
        get() = _uiState

    init {
        listenTransactions()
    }

    fun listenTransactions(date: Date = Calendar.getInstance().time) {
        viewModelScope.launch {
            listenMonthlyTransactions(date)
                .onStart {
                    _uiState.value = Resource.Loading()
                }
                .catch { error ->
                    _uiState.value = Resource.Error(error.localizedMessage ?: "Unexpected error")
                }
                .collect { data ->
                    _uiState.value = Resource.Success(data)
                }
        }
    }
}