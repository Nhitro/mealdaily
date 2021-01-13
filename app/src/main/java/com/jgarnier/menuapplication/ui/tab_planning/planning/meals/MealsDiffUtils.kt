package com.jgarnier.menuapplication.ui.tab_planning.planning.meals

import androidx.recyclerview.widget.DiffUtil

class MealsDiffUtils : DiffUtil.ItemCallback<SelectableMealWithDishes>() {

    override fun areItemsTheSame(oldItem: SelectableMealWithDishes, newItem: SelectableMealWithDishes): Boolean {
        return oldItem.mealWithDishes == newItem.mealWithDishes
    }

    override fun areContentsTheSame(oldItem: SelectableMealWithDishes, newItem: SelectableMealWithDishes): Boolean {
        return oldItem.mealWithDishes.meal.idMeal == newItem.mealWithDishes.meal.idMeal
                && oldItem.mealWithDishes.dishes == newItem.mealWithDishes.dishes
    }

}