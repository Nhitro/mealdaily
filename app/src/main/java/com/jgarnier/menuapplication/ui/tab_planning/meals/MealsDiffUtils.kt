package com.jgarnier.menuapplication.ui.tab_planning.meals

import androidx.recyclerview.widget.DiffUtil
import com.jgarnier.menuapplication.data.entity.MealWithDishes

class MealsDiffUtils : DiffUtil.ItemCallback<MealWithDishes>() {

    override fun areItemsTheSame(oldItem: MealWithDishes, newItem: MealWithDishes): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MealWithDishes, newItem: MealWithDishes): Boolean {
        return oldItem.meal.idMeal == newItem.meal.idMeal && oldItem.dishes == newItem.dishes
    }

}