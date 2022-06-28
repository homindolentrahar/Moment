package com.homindolentrahar.moment.features.transaction.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.homindolentrahar.moment.databinding.TransactionListItemBinding
import com.homindolentrahar.moment.features.transaction.domain.model.Transaction
import java.time.LocalDateTime

class TransactionsAdapter(
    private val onClick: (item: Transaction) -> Unit
) :
    ListAdapter<Transaction, TransactionsAdapter.TransactionViewHolder>(TransactionComparator) {

    class TransactionViewHolder(private val binding: TransactionListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Transaction) {
            binding.tvTransactionName.text = item.name
        }

        companion object {
            fun inflate(context: Context, parent: ViewGroup): TransactionViewHolder {
                val binding =
                    TransactionListItemBinding.inflate(LayoutInflater.from(context), parent, false)

                return TransactionViewHolder(binding)
            }
        }
    }

    companion object TransactionComparator : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder =
        TransactionViewHolder.inflate(parent.context, parent)

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
        holder.itemView.setOnClickListener { onClick(item) }
    }
}