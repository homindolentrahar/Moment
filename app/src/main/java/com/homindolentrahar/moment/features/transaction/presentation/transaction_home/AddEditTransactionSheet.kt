package com.homindolentrahar.moment.features.transaction.presentation.transaction_home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.homindolentrahar.moment.R
import com.homindolentrahar.moment.databinding.AddEditTransactionSheetBinding
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import com.homindolentrahar.moment.features.transaction.domain.model.TransactionType
import com.homindolentrahar.moment.features.transaction.presentation.transaction_detail.TransactionDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch
import java.util.*

enum class AddEditTransactionSheetType(name: String) {
    ADD("add"), EDIT("edit")
}

@AndroidEntryPoint
class AddEditTransactionSheet() : BottomSheetDialogFragment() {
    private lateinit var binding: AddEditTransactionSheetBinding
    private val viewModel: TransactionDetailViewModel by viewModels()
    private val args = arguments

    companion object {
        val TAG = AddEditTransactionSheet::class.java.simpleName
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
                            "Signing In",
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
                    } else if (!state.loading && state.error.isNotBlank()) {
                        Log.d(TAG, "Operation success success!")

                        dismiss()
                    }
                }
            }
        }

        val type = arguments?.getString("type") ?: AddEditTransactionSheetType.ADD.name

        binding.chipsType.setOnCheckedStateChangeListener { group, checkedIds ->
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
        }

        if (type == AddEditTransactionSheetType.EDIT.name) {
            val id = arguments?.getString("id")

            id?.let {
                viewModel.singleTransaction(it)
            }

            binding.textHeader.text = "Edit Transaction"
            binding.btnPrimaryAction.text = "Update"
        } else {
            binding.textHeader.text = "New Transaction"
            binding.btnPrimaryAction.text = "Save"
        }

        binding.btnDelete.setOnClickListener {
            arguments?.let { bundle ->
                bundle.getString("id")?.let { id ->
                    viewModel.remove(id)
                }
            }
        }

        binding.btnPrimaryAction.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val desc = binding.editTextDesc.text.toString()
            val amount = binding.editTextAmount.text.toString().toDouble()

            if (type == AddEditTransactionSheetType.ADD.name) {
//                val transaction = Transaction(
//                    name = name,
//                    desc = desc,
//                    type = TransactionType.valueOf(type),
//                    amount = amount,
//                    category =,
//                    timestamp = Date()
//                )
            } else {

            }
        }
    }

    private fun populateData(transaction: Transaction) {
        val editName = requireView().findViewById<TextInputEditText>(R.id.edit_text_name)
        val editDesc = requireView().findViewById<TextInputEditText>(R.id.edit_text_desc)
        val editAmount = requireView().findViewById<TextInputEditText>(R.id.edit_text_amount)

        editName.setText(transaction.name)
        editDesc.setText(transaction.desc)
        editAmount.setText(transaction.amount.toString())
    }
}