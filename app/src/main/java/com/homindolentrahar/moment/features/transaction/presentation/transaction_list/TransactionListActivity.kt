package com.homindolentrahar.moment.features.transaction.presentation.transaction_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.homindolentrahar.moment.R
import com.homindolentrahar.moment.databinding.ActivityTransactionListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionListActivity : AppCompatActivity() {
    private lateinit var binding:ActivityTransactionListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTransactionListBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}