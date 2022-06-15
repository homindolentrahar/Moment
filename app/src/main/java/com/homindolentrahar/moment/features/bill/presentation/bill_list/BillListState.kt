package com.homindolentrahar.moment.features.bill.presentation.bill_list

import com.homindolentrahar.moment.features.bill.domain.model.Bill

data class BillListState(
    val bills: List<Bill> = emptyList(),
    val loading: Boolean = false,
    val error: String = ""
)

