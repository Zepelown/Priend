package com.example.priend.select.plant.view.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.priend.common.view.RecyclerViewItemClickListener
import com.example.priend.databinding.ItemSelectPlantBinding

class SelectPlantViewHolder(
    private val binding: ItemSelectPlantBinding,
    private val clickListener: RecyclerViewItemClickListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{
    init {
        binding.root.setOnClickListener(this)
    }

    fun bind(item : SelectPlantItem){
        binding.itemSelectPlantImage.apply {
            Glide.with(binding.root.context)
                .load(item.imageUrl)
                .skipMemoryCache(true)
                .apply(RequestOptions.fitCenterTransform())
                .into(this)
        }

        binding.itemSelectPlantTitle.apply {
            text = item.title
        }

    }

    override fun onClick(v: View?) {
        val position = adapterPosition
        if (position != RecyclerView.NO_POSITION) {
            clickListener.onItemClick(position)
        }
    }

}