package com.jgarnier.menuapplication.ui.tab_planning.dishes

import androidx.recyclerview.widget.DiffUtil
import com.jgarnier.menuapplication.data.entity.Dish

/**
 *
 */
class DishDiffUtils : DiffUtil.ItemCallback<Dish>() {

    override fun areItemsTheSame(oldItem: Dish, newItem: Dish): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Dish, newItem: Dish): Boolean {
        return newItem.idDish == oldItem.idDish
                && newItem.dishName == oldItem.dishName
                && newItem.dishDescription == oldItem.dishDescription
    }

}