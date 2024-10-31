package com.example.priend.main.info.view.recyclerview.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.priend.common.view.RecyclerViewItemClickListener
import com.example.priend.databinding.ItemInfoBinding
import com.example.priend.main.info.view.recyclerview.InfoItem

class InfoPlantViewHolder(
    private val binding: ItemInfoBinding,
    private val clickListener: RecyclerViewItemClickListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, InfoViewHolder {
    init {
        binding.root.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    override fun bind(item: InfoItem) {
        binding.apply {
            itemInfoPlantName.text = item.title
            itemInfoTemperature.text = item.temperature
            itemInfoStartDate.text = item.startDate
            itemInfoSoilMoisture.text = item.soilMoisture
        }
    }
}