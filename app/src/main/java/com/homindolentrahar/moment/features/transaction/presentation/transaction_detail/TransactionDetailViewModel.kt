package com.homindolentrahar.moment.features.transaction.presentation.transaction_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.transaction.domain.usecase.GetSingleTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    private val getSingleTransaction: GetSingleTransaction
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionDetailState())
    val state: StateFlow<TransactionDetailState>
        get() = _state

    fun singleTransaction(id: String) {
        viewModelScope.launch {
            getSingleTransaction(id)
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
                                transaction = resource.data,
                                error = "",
                                loading = false
                            )
                        }
                    }
                }
        }
    }

}