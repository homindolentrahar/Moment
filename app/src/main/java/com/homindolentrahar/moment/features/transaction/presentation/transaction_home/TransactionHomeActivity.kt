package com.homindolentrahar.moment.features.transaction.presentation.transaction_home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.homindolentrahar.moment.databinding.ActivityTransactionHomeBinding
import com.homindolentrahar.moment.features.transaction.presentation.TransactionsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransactionHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionHomeBinding
    private val viewModel: TransactionHomeViewModel by viewModels()
    private val TAG = TransactionHomeActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTransactionHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

//        val modalBottomSheet = BottomSheet()

        binding.selectDate.setOnClickListener {
//            Show select date dialog
        }

        binding.fabAdd.setOnClickListener {
//            Show add transaction bottom sheet
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { state ->
                    if (state.loading) {
                        Log.d(TAG, "Loading")
                    } else if (state.error.isNotBlank()) {
                        Log.d(TAG, "Error: ${state.error}")
                    } else if (state.transactions.isNotEmpty()) {
                        Log.d(TAG, "Transactions: ${state.transactions.size}")

                        val adapter = TransactionsAdapter { transaction ->
//                            Show Transaction Item
                        }

                        binding.rvRecentTransaction.adapter = adapter
                    } else {
                        Log.d(TAG, "Success")
                    }
                }
            }
        }
    }
}