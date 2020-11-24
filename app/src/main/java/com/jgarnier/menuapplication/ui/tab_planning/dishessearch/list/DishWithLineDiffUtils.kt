package com.jgarnier.menuapplication.ui.tab_planning.dishessearch.list

import androidx.recyclerview.widget.DiffUtil
import com.jgarnier.menuapplication.data.entity.DishWithLines

class DishWithLineDiffUtils : DiffUtil.ItemCallback<DishWithLines>() {

    override fun areItemsTheSame(oldItem: DishWithLines, newItem: DishWithLines): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DishWithLines, newItem: DishWithLines): Boolean {
        return oldItem.dish.idDish == newItem.dish.idDish
    }

}