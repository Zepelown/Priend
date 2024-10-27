package com.example.priend.main.info.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.priend.common.view.RecyclerViewItemClickListener
import com.example.priend.databinding.ItemInfoBinding

class InfoViewAdapter(
    private val plantsInfo : List<InfoItem>,
    private val clickListener: RecyclerViewItemClickListener
) : RecyclerView.Adapter<InfoViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        return InfoViewHolder(
            ItemInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            clickListener
        )
    }

    override fun getItemCount(): Int = plantsInfo.count()

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        val item = plantsInfo.get(position)
        holder.bind(item)
    }

}