package com.codehub.finmanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codehub.finmanager.databinding.ItemBudgetBinding
import com.codehub.finmanager.model.Budget

class BudgetAdapter :ListAdapter<Budget, BudgetAdapter.BudgetViewHolder>(BudgetDiffUtil){
    inner class BudgetViewHolder(private val binding: ItemBudgetBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(budget: Budget){
            binding.tvBudgetItemTitle.text = budget.tittle
            binding.ivBudgetItemImage.setImageResource(budget.image)
            binding.tvBudgetItemPercent.text = String.format("%.1f",(budget.spended/budget.budget)*100) + "%"
            binding.tvBudgetItemRatio.text = "${budget.spended}/${budget.budget}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
       val binding = ItemBudgetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BudgetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object BudgetDiffUtil :DiffUtil.ItemCallback<Budget>(){
    override fun areItemsTheSame(oldItem: Budget, newItem: Budget): Boolean {
       return oldItem.tittle == newItem.tittle
    }

    override fun areContentsTheSame(oldItem: Budget, newItem: Budget): Boolean {
       return oldItem == newItem
    }

}
