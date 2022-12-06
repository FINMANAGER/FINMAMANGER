package com.codehub.finmanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codehub.finmanager.R
import com.codehub.finmanager.databinding.ItemBudgetBinding
import com.codehub.finmanager.databinding.ItemTransactionBinding
import com.codehub.finmanager.model.Budget
import com.codehub.finmanager.model.Transaction

class TransactionsAdapter :ListAdapter<Transaction, TransactionsAdapter.TransactionsViewHolder>(TransactionsDiffUtil){
    inner class TransactionsViewHolder(private val binding: ItemTransactionBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction){
            binding.apply {
                tvAmount.text = "${transaction.amount} $"
                tvCategory.text = transaction.category
                tvDate.text = transaction.date
                tvTransactionTitle.text = transaction.title
            }
            if(transaction.isIncome == true){
               binding.typeIcon.setImageResource(R.drawable.ic_moneyin_svgrepo_com)
            }else{
                binding.typeIcon.setImageResource(R.drawable.ic_moneyout_svgrepo_com)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
       val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object TransactionsDiffUtil :DiffUtil.ItemCallback<Transaction>(){
    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
       return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
       return oldItem == newItem
    }

}
