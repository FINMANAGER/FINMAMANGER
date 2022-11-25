package com.codehub.finmanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codehub.finmanager.databinding.ItemBudgetBinding
import com.codehub.finmanager.databinding.ItemTransactionPictureBinding
import com.codehub.finmanager.model.Budget

class PictureAdapter :ListAdapter<Int, PictureAdapter.PictureViewHolder>(PictureDiffUtil){
    inner class PictureViewHolder(private val binding: ItemTransactionPictureBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(picture:Int){
            binding.imageView.setImageResource(picture)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
       val binding = ItemTransactionPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object PictureDiffUtil :DiffUtil.ItemCallback<Int>(){
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
       return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
       return oldItem == newItem
    }

}
