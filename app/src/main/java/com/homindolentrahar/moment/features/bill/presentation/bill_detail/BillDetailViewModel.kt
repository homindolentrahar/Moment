package com.homindolentrahar.moment.features.bill.presentation.bill_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.features.bill.domain.model.Bill
import com.homindolentrahar.moment.features.bill.domain.usecase.GetSingleBill
import com.homindolentrahar.moment.features.bill.domain.usecase.RemoveBill
import com.homindolentrahar.moment.features.bill.domain.usecase.UpdateBill
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillDetailViewModel @Inject constructor(
    private val getSingleBill: GetSingleBill,
    private val updateBill: UpdateBill,
    private val removeBill: RemoveBill
) : ViewModel() {

    private val _state = MutableStateFlow(BillDetailState())
    val state: StateFlow<BillDetailState>
        get() = _state

    fun singleBill(id: String) {
        viewModelScope.launch {
            getSingleBill(id)
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
                .collect { data ->
                    _state.value = _state.value.copy(
                        error = "",
                        loading = false,
                        bill = data
                    )
                }
        }
    }

    fun update(id: String, bill: Bill) {
        viewModelScope.launch {
            updateBill(id, bill)
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
                .collect { data ->
                    _state.value = _state.value.copy(
                        error = "",
                        loading = false,
                    )
                }
        }
    }

    fun remove(id: String) {
        viewModelScope.launch {
            removeBill(id)
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
                .collect { data ->
                    _state.value = _state.value.copy(
                        error = "",
                        loading = false,
                    )
                }
        }
    }

}