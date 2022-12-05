package com.codehub.finmanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codehub.finmanager.R
import com.codehub.finmanager.databinding.ItemBudgetBinding
import com.codehub.finmanager.model.Budget

class BudgetAdapter(val clickListener:(Budget)->Unit) :ListAdapter<Budget, BudgetAdapter.BudgetViewHolder>(BudgetDiffUtil){
    inner class BudgetViewHolder(private val binding: ItemBudgetBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(budget: Budget){
            binding.tvBudgetItemTitle.text = budget.category
            binding.tvBudgetItemPercent.text = String.format("%.1f",(budget.spended/budget.budget)*100) + "%"
            binding.tvBudgetItemRatio.text = "${budget.spended}/${budget.budget}"
            when(budget.category){
                "Food/Beverage" -> binding.ivBudgetItemImage.setImageResource(R.drawable.ic_food)
                "Bill/Fees" -> binding.ivBudgetItemImage.setImageResource(R.drawable.ic_fees)
                "Travel/Transportation" -> binding.ivBudgetItemImage.setImageResource(R.drawable.ic_travel)
                "Wifi" -> binding.ivBudgetItemImage.setImageResource(R.drawable.ic_wifi)
                "Medicine" -> binding.ivBudgetItemImage.setImageResource(R.drawable.ic_medicine)
            }
            binding.root.setOnClickListener {
                clickListener(budget)
            }

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
       return oldItem.category == newItem.category
    }

    override fun areContentsTheSame(oldItem: Budget, newItem: Budget): Boolean {
       return oldItem == newItem
    }

}
