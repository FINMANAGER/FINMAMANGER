package com.codehub.finmanager.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codehub.finmanager.R
import com.codehub.finmanager.databinding.ItemVariableBinding
import com.codehub.finmanager.model.ChartItem


class ChartItemAdapter : ListAdapter<ChartItem, ChartItemAdapter.ChartViewHolder>(ChartDiffUtil){
    inner class ChartViewHolder(private val binding: ItemVariableBinding):RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(chartItem: ChartItem){
            binding.tvChartItemName.text = chartItem.name
            binding.tvChartItemAmount.text = chartItem.amount.toString()
            binding.variableColor.apply {
                //setBackgroundColor(chartItem.color)
                background = binding.root.resources.getDrawable(R.drawable.circle_background).apply {
                    backgroundTintList = ColorStateList.valueOf(chartItem.color)
                }
            }

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