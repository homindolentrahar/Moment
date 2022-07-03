package com.homindolentrahar.moment.features.transaction.presentation.transaction_home

import android.content.Intent
import android.icu.text.CompactDecimalFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.homindolentrahar.moment.R
import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.databinding.ActivityTransactionHomeBinding
import com.homindolentrahar.moment.features.transaction.domain.model.TransactionType
import com.homindolentrahar.moment.features.transaction.presentation.TransactionsAdapter
import com.homindolentrahar.moment.features.transaction.presentation.transaction_detail.TransactionDetailActivity
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

                viewModel.listenTransactions(Date(timestamp))
            }
            addOnNegativeButtonClickListener {
                dismiss()
            }
        }

        binding.tvMonth.text = formattedNow
        binding.selectDate.setOnClickListener {
            datePicker.show(supportFragmentManager, "DatePicker")
        }
        binding.incomeBtnContinue.setOnClickListener {
            Intent(this, TransactionListActivity::class.java)
                .apply {
                    putExtra("type", TransactionType.INCOME.name)
                }
                .also {
                    startActivity(it)
                }
        }
        binding.outcomeBtnContinue.setOnClickListener {
            Intent(this, TransactionListActivity::class.java)
                .apply {
                    putExtra("type", TransactionType.EXPENSE.name)
                }
                .also {
                    startActivity(it)
                }
        }
        binding.tvBtnViewAllTransaction.setOnClickListener {
            val intent = Intent(this, TransactionListActivity::class.java)

            startActivity(intent)
        }
        binding.fabAdd.setOnClickListener {
//            modalBottomSheet.arguments = bundleOf(
//                "type" to AddEditTransactionSheetType.ADD.value
//            )
//            modalBottomSheet.show(supportFragmentManager, AddEditTransactionSheet.TAG)
            val intent = Intent(this, TransactionDetailActivity::class.java)

            intent.apply {
                putExtra("type", AddEditTransactionSheetType.ADD.value)
            }

            startActivity(intent)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is Resource.Error -> {
                            Log.d(TAG, "Error: ${state.message}")

                            Toasty.error(
                                this@TransactionHomeActivity,
                                state.message ?: "Unexpected Error",
                                Toast.LENGTH_LONG,
                                true
                            )
                                .show()
                        }
                        is Resource.Loading -> {
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
                        }
                        is Resource.Success -> {
                            val adapter = TransactionsAdapter { transaction ->
                                val intent = Intent(
                                    this@TransactionHomeActivity,
                                    TransactionDetailActivity::class.java
                                )

                                intent.apply {
                                    putExtra("data", transaction)
                                    putExtra("type", AddEditTransactionSheetType.EDIT.value)
                                }

                                startActivity(intent)
//                                modalBottomSheet.arguments = bundleOf(
//                                    "data" to transaction,
//                                    "type" to AddEditTransactionSheetType.EDIT.value,
//                                )
//                                modalBottomSheet.show(
//                                    supportFragmentManager,
//                                    AddEditTransactionSheet.TAG
//                                )
                            }

                            val income =
                                (state.data?.filter { it.type == TransactionType.INCOME }
                                    ?.sumOf { it.amount } ?: 0).toDouble()
                            val outcome =
                                (state.data?.filter { it.type == TransactionType.EXPENSE }
                                    ?.sumOf { it.amount } ?: 0).toDouble()
                            val incomeText = CompactDecimalFormat.getInstance(
                                Locale.US,
                                CompactDecimalFormat.CompactStyle.SHORT
                            )
                                .format(income)
                            val outcomeText = CompactDecimalFormat.getInstance(
                                Locale.US,
                                CompactDecimalFormat.CompactStyle.SHORT
                            )
                                .format(outcome)
                            val balance = CompactDecimalFormat.getInstance()
                                .format(income - outcome)

                            adapter.submitList(state.data ?: emptyList())

                            state.data?.let {
                                if (it.isNotEmpty()) {
                                    binding.noTransactionContainer.root.visibility = View.GONE
                                    binding.rvRecentTransaction.visibility = View.VISIBLE
                                } else {
                                    binding.noTransactionContainer.root.visibility = View.VISIBLE
                                    binding.rvRecentTransaction.visibility = View.GONE
                                }
                            }

                            binding.tvBalance.text = "Rp $balance"
                            binding.tvIncome.text = incomeText
                            binding.tvOutcome.text = outcomeText
                            binding.rvRecentTransaction.adapter = adapter
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}