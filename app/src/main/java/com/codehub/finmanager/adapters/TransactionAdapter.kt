package com.codehub.finmanager.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codehub.finmanager.R
import com.codehub.finmanager.databinding.ItemTransactionBinding
import com.codehub.finmanager.model.Transaction


class TransactionAdapter : ListAdapter<Transaction, TransactionAdapter.HistoryViewHolder>(TransactionDiffUtil){
    inner class HistoryViewHolder(private val binding: ItemTransactionBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction){
            binding.tvTransactionTitle.text = transaction.title
            binding.tvTransactionDate.text = transaction.date
            binding.tvTransactionDesc.text = transaction.description
            binding.ivTransactionImage.setImageResource(transaction.imageUrl)
            if (transaction.isIncome){
            binding.tvAmount.setTextColor(Color.GREEN)
            }else{
                binding.tvAmount.setTextColor(Color.RED)
            }
        }
    }

    object TransactionDiffUtil: DiffUtil.ItemCallback<Transaction>(){
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}