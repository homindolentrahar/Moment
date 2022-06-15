package com.homindolentrahar.moment.features.bill.presentation.bill_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.features.bill.domain.model.BillStatus
import com.homindolentrahar.moment.features.bill.domain.usecase.GetBillList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class BillListViewModel @Inject constructor(
    private val getBillList: GetBillList
) : ViewModel() {

    private val _state = MutableStateFlow(BillListState())
    val state: StateFlow<BillListState>
        get() = _state

    init {
        billList()
    }


    fun billList(
        query: String = "",
        status: BillStatus = BillStatus.USING,
        categoryId: String = "",
        date: LocalDateTime = LocalDateTime.now()
    ) {
        viewModelScope.launch {
            getBillList(
                query = query,
                status = status,
                categoryId = categoryId,
                date = date
            )
                .onStart {
                    _state.value = _state.value.copy(
                        loading = true,
                        error = "",
                    )
                }
                .catch { error ->
                    _state.value = _state.value.copy(
                        loading = false,
                        error = error.localizedMessage!!.toString()
                    )
                }
                .collect {
                    _state.value = _state.value.copy(
                        loading = false,
                        error = "",
                        bills = it
                    )
                }
        }
    }
}