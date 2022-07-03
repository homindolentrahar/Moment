package com.homindolentrahar.moment.features.transaction.presentation.transaction_list

import android.content.Intent
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
import com.homindolentrahar.moment.R
import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.databinding.ActivityTransactionListBinding
import com.homindolentrahar.moment.features.transaction.domain.model.TransactionType
import com.homindolentrahar.moment.features.transaction.presentation.TransactionsAdapter
import com.homindolentrahar.moment.features.transaction.presentation.transaction_detail.TransactionDetailActivity
import com.homindolentrahar.moment.features.transaction.presentation.transaction_home.AddEditTransactionSheet
import com.homindolentrahar.moment.features.transaction.presentation.transaction_home.AddEditTransactionSheetType
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransactionListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionListBinding
    private val viewModel: TransactionListViewModel by viewModels()
    private val TAG = TransactionListActivity::class.java.simpleName
    private var selectedCategory: String = ""
    private var selectedType: String = TransactionType.ALL.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTransactionListBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val types =
            TransactionType.values().toList().map { it.value }

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.spinnerType.apply {
            setItems(types)
            selectItemByIndex(0)
            setOnSpinnerOutsideTouchListener { _, _ ->
                dismiss()
            }
            setOnSpinnerItemSelectedListener<String> { _, _, newIndex, newText ->
                val categories = when (newIndex) {
                    1 -> {
                        resources.getStringArray(R.array.income_categories).toList()
                    }
                    2 -> {
                        resources.getStringArray(R.array.expense_categories).toList()
                    }
                    else -> {
                        emptyList<String>()
                    }
                }

                binding.spinnerCategory.setItems(categories)
                binding.spinnerCategory.clearSelectedItem()
                selectedType = newText
            }
        }
        binding.spinnerCategory.apply {
            setOnSpinnerOutsideTouchListener { _, _ ->
                dismiss()
            }
            setOnSpinnerItemSelectedListener<String> { _, _, _, newItem ->
                selectedCategory = newItem
            }
        }
        binding.btnFilter.setOnClickListener {
            viewModel.listenTransactions(
                type = if (selectedType.isEmpty()) TransactionType.ALL else TransactionType.valueOf(
                    selectedType.uppercase()
                ),
                categoryId = selectedCategory,
            )
        }

        intent.getStringExtra("type")?.let { type ->
            selectedType = type

            val index = when (selectedType) {
                TransactionType.ALL.name -> 0
                TransactionType.INCOME.name -> 1
                TransactionType.EXPENSE.name -> 2
                else -> 0
            }
            binding.spinnerType.selectItemByIndex(index)
        }

        viewModel.listenTransactions(type = TransactionType.valueOf(selectedType.uppercase()))

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is Resource.Error -> {
                            Log.d(TAG, "Error: ${state.message}")

                            Toasty.error(
                                this@TransactionListActivity,
                                state.message ?: "Unexpected error",
                                Toast.LENGTH_LONG,
                                true
                            )
                                .show()
                        }
                        is Resource.Loading -> {
                            Log.d(TAG, "Loading...")

                            Toasty.custom(
                                this@TransactionListActivity,
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
                                    this@TransactionListActivity,
                                    TransactionDetailActivity::class.java
                                )

                                intent.apply {
                                    putExtra("data", transaction)
                                    putExtra("type", AddEditTransactionSheetType.EDIT.value)
                                }

                                startActivity(intent)
                            }

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

                            binding.rvRecentTransaction.adapter = adapter
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}