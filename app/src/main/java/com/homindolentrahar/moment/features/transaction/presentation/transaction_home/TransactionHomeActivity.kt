package com.homindolentrahar.moment.features.transaction.presentation.transaction_home

import android.content.Intent
import android.icu.number.Notation
import android.icu.number.NumberFormatter
import android.icu.number.Precision
import android.icu.text.CompactDecimalFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.homindolentrahar.moment.R
import com.homindolentrahar.moment.databinding.ActivityTransactionHomeBinding
import com.homindolentrahar.moment.features.transaction.presentation.TransactionsAdapter
import com.homindolentrahar.moment.features.transaction.presentation.transaction_list.TransactionListActivity
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
        val dateConstraint = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointBackward.now())
            .setEnd(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pick transaction month")
            .setPositiveButtonText("Pick")
            .setNegativeButtonText("Cancel")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(dateConstraint)
            .build()
        val dateFormatter = SimpleDateFormat("MMMM yyyy")
        val formattedNow = dateFormatter.format(now)

        datePicker.apply {
            addOnPositiveButtonClickListener { timestamp ->
                binding.tvMonth.text = dateFormatter.format(Date(timestamp))

                viewModel.allTransactions(Date(timestamp))
            }
            addOnNegativeButtonClickListener {
                dismiss()
            }
        }

//        Populate initial data
        binding.tvMonth.text = formattedNow
        binding.selectDate.setOnClickListener {
            datePicker.show(supportFragmentManager, "DatePicker")
        }

        binding.tvBtnViewAllTransaction.setOnClickListener {
            val intent = Intent(this, TransactionListActivity::class.java)

            startActivity(intent)
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
                        val income = CompactDecimalFormat.getInstance(
                            Locale.US,
                            CompactDecimalFormat.CompactStyle.SHORT
                        )
                            .format(state.income.sumOf { it.amount })
                            .toString()
                        val outcome = CompactDecimalFormat.getInstance(
                            Locale.US,
                            CompactDecimalFormat.CompactStyle.SHORT
                        )
                            .format(state.expenses.sumOf { it.amount })
                            .toString()

                        adapter.submitList(state.transactions)

                        if (state.transactions.isNotEmpty()) {
                            binding.noTransactionContainer.root.visibility = View.GONE
                            binding.rvRecentTransaction.visibility = View.VISIBLE
                        } else {
                            binding.noTransactionContainer.root.visibility = View.VISIBLE
                            binding.rvRecentTransaction.visibility = View.GONE
                        }

                        binding.tvBalance.text = "Rp ${balance.toInt()}"
                        binding.tvIncome.text = "$income"
                        binding.tvOutcome.text = "$outcome"
                        binding.rvRecentTransaction.adapter = adapter
                    }
                }
            }
        }
    }
}