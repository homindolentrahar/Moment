package com.homindolentrahar.moment.features.bill.presentation.bill_detail

import com.homindolentrahar.moment.features.bill.domain.model.Bill

data class BillDetailState(
    val bill: Bill? = null,
    val loading: Boolean = false,
    val error: String = ""
)

