package com.homindolentrahar.moment.features.transaction.presentation.transaction_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.usecase.RemoveTransaction
import com.homindolentrahar.moment.features.transaction.domain.usecase.UpdateTransaction
import com.homindolentrahar.moment.features.transaction.domain.usecase.GetSingleTransaction
import com.homindolentrahar.moment.features.transaction.domain.usecase.SaveTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    private val getSingleTransaction: GetSingleTransaction,
    private val saveTransaction: SaveTransaction,
    private val updateTransaction: UpdateTransaction,
    private val removeTransaction: RemoveTransaction
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionDetailState())
    val state: StateFlow<TransactionDetailState>
        get() = _state

    fun singleTransaction(id: String) {
        viewModelScope.launch {
            getSingleTransaction(id)
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
                .collect { transaction ->
                    _state.value = _state.value.copy(
                        error = "",
                        transaction = transaction,
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
                        loading = false,
                    )
                }
        }
    }

    fun update(id: String, transaction: Transaction) {
        viewModelScope.launch {
            updateTransaction(id, transaction)
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
                        loading = false,
                    )
                }
        }
    }

    fun remove(id: String) {
        viewModelScope.launch {
            removeTransaction(id)
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
                        loading = false,
                    )
                }
        }
    }
}