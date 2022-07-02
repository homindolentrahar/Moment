package com.homindolentrahar.moment.features.transaction.presentation.transaction_home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.homindolentrahar.moment.R
import com.homindolentrahar.moment.databinding.AddEditTransactionSheetBinding
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.model.TransactionType
import com.homindolentrahar.moment.features.transaction.presentation.transaction_detail.TransactionDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

enum class AddEditTransactionSheetType(val value: String) {
    ADD("add"), EDIT("edit")
}

@AndroidEntryPoint
class AddEditTransactionSheet : BottomSheetDialogFragment() {
    private lateinit var binding: AddEditTransactionSheetBinding
    private val viewModel: TransactionDetailViewModel by viewModels()
    private var selectedCategory = ""
    private var date = ""

    companion object {
        val TAG: String = AddEditTransactionSheet::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddEditTransactionSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state ->
                    if (state.loading) {
                        Log.d(TAG, "Loading...")

                        Toasty.custom(
                            requireContext(),
                            "Processing",
                            R.drawable.loading,
                            R.color.black,
                            Toast.LENGTH_LONG,
                            true,
                            true
                        )
                            .show()
                    } else if (state.error.isNotBlank()) {
                        Log.d(TAG, "Error: ${state.error}")

                        Toasty.error(requireContext(), state.error, Toast.LENGTH_LONG, true)
                            .show()

                        dismiss()
                    } else if (state.transaction != null) {
                        populateData(state.transaction)
                    } else  {
                        Log.d(TAG, "Operation success success!")

                        dismiss()
                    }
                }
            }
        }

        val type = arguments?.getString("type") ?: AddEditTransactionSheetType.ADD.value
        val transactionId = arguments?.getString("id") ?: ""
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pick transaction date")
            .setPositiveButtonText("Pick")
            .setNegativeButtonText("Cancel")
            .build()

        if (type == AddEditTransactionSheetType.EDIT.value) {
            binding.textHeader.text = "Edit Transaction"
            binding.btnPrimaryAction.text = "Update"
            binding.btnDelete.visibility = View.VISIBLE

            viewModel.singleTransaction(transactionId)
        } else {
            binding.textHeader.text = "New Transaction"
            binding.btnPrimaryAction.text = "Save"
            binding.btnDelete.visibility = View.GONE
        }

        datePicker.apply {
            addOnPositiveButtonClickListener { timestamp ->
                date = SimpleDateFormat("dd MMMM yyyy").format(Date(timestamp))
                binding.tvPickDate.text = date
            }
            addOnNegativeButtonClickListener {
                dismiss()
            }
        }

        binding.selectCategory.apply {
            setOnSpinnerItemSelectedListener<String> { _, _, _, newItem ->
                selectedCategory = newItem
            }
            setItems(
                resources.getStringArray(R.array.expense_categories).toList()
            )
            setOnSpinnerOutsideTouchListener { _, _ ->
                dismiss()
            }
        }
        binding.chipsType.setOnCheckedStateChangeListener { _, checkedIds ->
            val categories = when (checkedIds.first()) {
                R.id.chip_expense -> {
                    resources.getStringArray(R.array.expense_categories).toList()
                }
                R.id.chip_income -> {
                    resources.getStringArray(R.array.income_categories).toList()
                }
                else -> {
                    emptyList<String>()
                }
            }

            binding.selectCategory.setItems(categories)
            binding.selectCategory.clearSelectedItem()
        }
        binding.pickDate.setOnClickListener {
            datePicker
                .show(parentFragmentManager, AddEditTransactionSheet::class.java.simpleName)
        }
        binding.btnDelete.setOnClickListener {
            if (transactionId.isNotEmpty()) {
                viewModel.remove(transactionId)
            }
        }
        binding.btnPrimaryAction.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val desc = binding.editTextDesc.text.toString()
            val amount = binding.editTextAmount.text.toString().toDouble()
            val category = selectedCategory
            val transactionType =
                if (binding.chipsType.checkedChipId == R.id.chip_expense) TransactionType.valueOf("EXPENSE")
                else TransactionType.valueOf("INCOME")
            val pickedDate = binding.tvPickDate.text.toString()

            if (name.isEmpty()) {
                binding.editTextName.error = "Name cannot be empty!"
            }
            if (amount <= 0.0) {
                binding.editTextAmount.error = "Amount cannot be zero or less!"
            }
            if (selectedCategory.isEmpty()) {
                binding.selectCategory.error = "Select a category!"
            }
            if (pickedDate.isEmpty()) {
                binding.tvPickDate.apply {
                    text = "Pick transaction date!"
                    setTextColor(R.color.red_fg)
                }
            }

            if (name.isNotEmpty() && amount > 0 && selectedCategory.isNotEmpty() && pickedDate.isNotEmpty()) {
                if (type == AddEditTransactionSheetType.ADD.name) {
                    val transaction = Transaction(
                        name = name,
                        desc = desc,
                        type = transactionType,
                        amount = amount,
                        category = category,
                        createdAt = SimpleDateFormat("dd MMMM yyyy").parse(pickedDate)
                            ?: Calendar.getInstance().time,
                        updatedAt = Calendar.getInstance().time
                    )

                    viewModel.save(transaction)
                } else {
                    val transaction = Transaction(
                        name = name,
                        desc = desc,
                        type = transactionType,
                        amount = amount,
                        category = category,
                        createdAt = SimpleDateFormat("dd MMMM yyyy").parse(pickedDate)
                            ?: Calendar.getInstance().time,
                        updatedAt = Calendar.getInstance().time
                    )

                    viewModel.update(transactionId, transaction)
                }
            }
        }
    }

    private fun populateData(transaction: Transaction) {
        val typeId =
            if (transaction.type == TransactionType.INCOME) R.id.chip_income else R.id.chip_expense
        val categoryIndex = when (transaction.type) {
            TransactionType.INCOME -> {
                val categories = resources.getStringArray(R.array.income_categories).toList()

                categories.indexOf(transaction.category)
            }
            TransactionType.EXPENSE -> {
                val categories = resources.getStringArray(R.array.expense_categories).toList()

                categories.indexOf(transaction.category)
            }
            else -> {
                -1
            }
        }

        binding.chipsType.check(typeId)
        binding.editTextName.setText(transaction.name)
        binding.editTextDesc.setText(transaction.desc)
        binding.editTextAmount.setText(transaction.amount.toString())
        binding.selectCategory.selectItemByIndex(categoryIndex)
        binding.tvPickDate.text = SimpleDateFormat("dd MMMM yyyy").format(transaction.createdAt)
    }
}