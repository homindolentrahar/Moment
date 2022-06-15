package com.homindolentrahar.moment.features.bill.presentation.bill_home

import com.homindolentrahar.moment.features.bill.domain.model.Bill

data class BillHomeState(
    val due: List<Bill> = emptyList(),
    val monthly: List<Bill> = emptyList(),
    val loading: Boolean = false,
    val error: String = ""
)

