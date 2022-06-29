package com.homindolentrahar.moment.features.transaction.presentation.transaction_home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.homindolentrahar.moment.R
import com.homindolentrahar.moment.databinding.ActivityTransactionHomeBinding
import com.homindolentrahar.moment.features.transaction.presentation.TransactionsAdapter
import com.whiteelephant.monthpicker.MonthPickerDialog
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class TransactionHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionHomeBinding
    private val viewModel: TransactionHomeViewModel by viewModels()
    private val TAG = TransactionHomeActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTransactionHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val modalBottomSheet = AddEditTransactionSheet()
        val now = Calendar.getInstance().time
        val monthPickerBuilder = MonthPickerDialog.Builder(
            this,
            { selectedMonth, selectedYear ->

            },
            now.year,
            now.month
        )
        val formattedNow = SimpleDateFormat("MMMM yyyy").format(now)

//        Populate initial data
        binding.tvMonth.text = formattedNow

        binding.selectDate.setOnClickListener {
//            Show select date dialog
            monthPickerBuilder
                .build()
                .show()
        }

        binding.fabAdd.setOnClickListener {
//            Show add transaction bottom sheet
            modalBottomSheet.arguments = bundleOf(
                "type" to AddEditTransactionSheetType.ADD.name
            )
            modalBottomSheet.show(supportFragmentManager, AddEditTransactionSheet.TAG)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { state ->
                    if (state.loading) {
                        Log.d(TAG, "Loading")

                        Toasty.custom(
                            this@TransactionHomeActivity,
                            "Loading Data",
                            R.drawable.loading,
                            R.color.black,
                            Toast.LENGTH_LONG,
                            true,
                            true
                        )
                            .show()
                    } else if (state.error.isNotEmpty()) {
                        Log.d(TAG, "Error: ${state.error}")

                        Toasty.error(
                            this@TransactionHomeActivity,
                            state.error,
                            Toast.LENGTH_LONG,
                            true
                        )
                            .show()
                    } else {
                        Log.d(TAG, "Transactions: ${state.transactions.size}")

                        val adapter = TransactionsAdapter { transaction ->
//                            Show Transaction Item
                            modalBottomSheet.arguments = bundleOf(
                                "id" to transaction.id,
                                "type" to AddEditTransactionSheetType.EDIT.name,
                            )
                            modalBottomSheet.show(
                                supportFragmentManager,
                                AddEditTransactionSheet.TAG
                            )
                        }

                        val balance = state.transactions.sumOf { it.amount }
                        val income = state.income.sumOf { it.amount }
                        val outcome = state.income.sumOf { it.amount }

                        binding.tvBalance.text = "Rp ${balance.toInt()}"
                        binding.tvIncome.text = "Rp ${income.toInt()}"
                        binding.tvOutcome.text = "Rp ${outcome.toInt()}"
                        binding.rvRecentTransaction.adapter = adapter
                    }
                }
            }
        }
    }
}