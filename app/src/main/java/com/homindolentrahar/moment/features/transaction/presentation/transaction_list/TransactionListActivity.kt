package com.homindolentrahar.moment.features.transaction.presentation.transaction_list

import android.content.Intent
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
import com.homindolentrahar.moment.databinding.ActivityTransactionListBinding
import com.homindolentrahar.moment.features.transaction.presentation.TransactionsAdapter
import com.homindolentrahar.moment.features.transaction.presentation.transaction_home.AddEditTransactionSheet
import com.homindolentrahar.moment.features.transaction.presentation.transaction_home.AddEditTransactionSheetType
import com.homindolentrahar.moment.features.transaction.presentation.transaction_home.TransactionHomeActivity
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransactionListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionListBinding
    private val viewModel: TransactionListViewModel by viewModels()
    private val TAG = TransactionListActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTransactionListBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val modalBottomSheet = AddEditTransactionSheet()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { state ->
                    if (state.loading) {
                        Log.d(TAG, "Loading...")

                        Toasty.custom(
                            this@TransactionListActivity,
                            "Loadin Data",
                            R.drawable.loading,
                            R.color.black,
                            Toast.LENGTH_LONG,
                            true,
                            true
                        )
                            .show()
                    } else if (state.error.isNotBlank()) {
                        Log.d(TAG, "Error: ${state.error}")

                        Toasty.error(
                            this@TransactionListActivity,
                            state.error,
                            Toast.LENGTH_LONG,
                            true
                        )
                            .show()
                    } else if (state.transactions.isNotEmpty()) {
                        Log.d(TAG, "Transactions: ${state.transactions.size}")

                        val adapter = TransactionsAdapter { transaction ->
//                            Show Transaction Item
                            modalBottomSheet.arguments = bundleOf(
                                "type" to AddEditTransactionSheetType.EDIT
                            )
                            modalBottomSheet.show(
                                supportFragmentManager,
                                AddEditTransactionSheet.TAG
                            )
                        }

                        binding.rvRecentTransaction.adapter = adapter
                    }
                }
            }
        }
    }
}