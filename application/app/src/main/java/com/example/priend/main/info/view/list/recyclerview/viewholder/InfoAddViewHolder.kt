package com.example.priend.main.info.view.list.recyclerview.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.priend.common.view.RecyclerViewItemClickListener
import com.example.priend.databinding.ItemInfoAddBinding
import com.example.priend.main.info.view.list.recyclerview.InfoItem

class InfoAddViewHolder(
    private val binding: ItemInfoAddBinding,
    private val clickListener: RecyclerViewItemClickListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, InfoViewHolder {

    init {
        binding.root.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val position = adapterPosition
        if (position != RecyclerView.NO_POSITION) {
            clickListener.onItemClick(position)
        }
    }

    override fun bind(item: InfoItem) {

    }


}