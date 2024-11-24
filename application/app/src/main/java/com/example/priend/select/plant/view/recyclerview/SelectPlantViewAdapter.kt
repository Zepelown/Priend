package com.example.priend.select.plant.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.priend.common.view.RecyclerViewItemClickListener
import com.example.priend.databinding.ItemSelectPlantBinding

class SelectPlantViewAdapter(
    private val selectPlants : List<SelectPlantItem>,
    private val clickListener: RecyclerViewItemClickListener
) : RecyclerView.Adapter<SelectPlantViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectPlantViewHolder {
        return SelectPlantViewHolder(
            ItemSelectPlantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            clickListener
        )
    }

    override fun getItemCount(): Int = selectPlants.count()

    override fun onBindViewHolder(holder: SelectPlantViewHolder, position: Int) {
        val item = selectPlants.get(position)
        holder.bind(item)
    }
}