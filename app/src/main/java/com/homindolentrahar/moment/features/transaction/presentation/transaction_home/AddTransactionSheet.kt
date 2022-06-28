package com.homindolentrahar.moment.features.transaction.presentation.transaction_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.homindolentrahar.moment.R

class AddTransactionSheet : BottomSheetDialogFragment() {
    companion object {
        val TAG = AddTransactionSheet::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.add_transaction_sheet, container, false)
}