package com.homindolentrahar.moment.features.bill.presentation.bill_list

import com.homindolentrahar.moment.features.bill.domain.model.Bill

data class BillListState(
    val due: List<Bill> = emptyList(),
    val monthly: List<Bill> = emptyList(),
    val loading: Boolean = false,
    val error: String = ""
)

