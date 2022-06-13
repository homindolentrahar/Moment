package com.homindolentrahar.moment.features.bill.presentation.bill_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.features.bill.domain.model.Bill
import com.homindolentrahar.moment.features.bill.domain.usecase.GetDueBills
import com.homindolentrahar.moment.features.bill.domain.usecase.GetMonthlyBills
import com.homindolentrahar.moment.features.bill.domain.usecase.SaveBill
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillListViewModel @Inject constructor(
    private val getDueBills: GetDueBills,
    private val getMonthlyBills: GetMonthlyBills,
    private val saveBill: SaveBill,
) : ViewModel() {

    private val _state = MutableStateFlow(BillListState())
    val state: MutableStateFlow<BillListState>
        get() = _state

    init {
        getBillData()
    }

    private fun getBillData() {
        viewModelScope.launch {
            getDueBills()
                .onStart {
                    _state.value = _state.value.copy(
                        loading = true
                    )
                }
                .zip(getMonthlyBills()) { due, monthly ->
                    hashMapOf(
                        "due" to due,
                        "monthly" to monthly
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
                        due = data["due"] ?: emptyList(),
                        monthly = data["monthly"] ?: emptyList(),
                        loading = false
                    )
                }
        }
    }

    fun saveNewBill(bill: Bill) {
        viewModelScope.launch {
            saveBill(bill)
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