package com.example.priend.main.info.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.priend.common.view.RecyclerViewItemClickListener
import com.example.priend.databinding.ItemInfoBinding

class InfoViewHolder(
    private val binding: ItemInfoBinding,
    private val clickListener: RecyclerViewItemClickListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    init {
        binding.root.setOnClickListener(this)
    }

    fun bind(item: InfoItem) {

    }

    override fun onClick(v: View?) {

    }

}