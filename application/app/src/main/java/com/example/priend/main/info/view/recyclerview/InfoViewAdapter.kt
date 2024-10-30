package com.example.priend.main.info.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.priend.common.view.RecyclerViewItemClickListener
import com.example.priend.databinding.ItemInfoAddBinding
import com.example.priend.databinding.ItemInfoBinding
import com.example.priend.main.info.view.recyclerview.viewholder.InfoAddViewHolder
import com.example.priend.main.info.view.recyclerview.viewholder.InfoPlantViewHolder
import java.lang.RuntimeException

class InfoViewAdapter(
    private val plantsInfo : List<InfoItem>,
    private val clickListener: RecyclerViewItemClickListener
) : RecyclerView.Adapter<ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType){
            VIEW_TYPE_ADD -> {
                return InfoAddViewHolder(
                    ItemInfoAddBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    clickListener
                )
            }
            VIEW_TYPE_PLANT -> {
                return InfoPlantViewHolder(
                    ItemInfoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    clickListener
                )
            }
            else -> throw RuntimeException("알 수 없는 viewType")
        }



    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       when(holder){
           is InfoAddViewHolder -> {
                holder.bind(plantsInfo[position])
           }
           is InfoPlantViewHolder -> {
                holder.bind(plantsInfo[position])
           }
       }
    }

    override fun getItemViewType(position: Int): Int {
        if (plantsInfo[position].type == InfoItemType.PLANT){
            return VIEW_TYPE_PLANT
        }
        return  VIEW_TYPE_ADD
    }

    override fun getItemCount(): Int = plantsInfo.count()

    companion object{
        private const val VIEW_TYPE_PLANT = 0
        private const val VIEW_TYPE_ADD = 1
    }
}