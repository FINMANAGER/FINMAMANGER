package com.codehub.finmanager.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codehub.finmanager.R
import com.codehub.finmanager.databinding.ItemTransactionBinding
import com.codehub.finmanager.databinding.ItemVariableBinding
import com.codehub.finmanager.model.ChartItem
import com.codehub.finmanager.model.Transaction


class ChartItemAdapter : ListAdapter<ChartItem, ChartItemAdapter.ChartViewHolder>(ChartDiffUtil){
    inner class ChartViewHolder(private val binding: ItemVariableBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(chartItem: ChartItem){
            binding.tvChartItemName.text = chartItem.name
            binding.tvChartItemAmount.text = chartItem.amount.toString()
            binding.variableColor.setCardBackgroundColor(ColorStateList.valueOf(chartItem.color))
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartViewHolder {
        val binding = ItemVariableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object ChartDiffUtil:DiffUtil.ItemCallback<ChartItem>() {
        override fun areItemsTheSame(oldItem: ChartItem, newItem: ChartItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ChartItem, newItem: ChartItem): Boolean {
            return oldItem==newItem
        }

    }
}