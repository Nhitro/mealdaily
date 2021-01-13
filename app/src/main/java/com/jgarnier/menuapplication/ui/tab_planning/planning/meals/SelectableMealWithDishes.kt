package com.jgarnier.menuapplication.ui.tab_planning.planning.meals

import com.jgarnier.menuapplication.data.entity.MealWithDishes

data class SelectableMealWithDishes(
        val mealWithDishes: MealWithDishes,
        val isSelected: Boolean,
        val isStateChanging: Boolean
) {

    override fun equals(other: Any?): Boolean {
        return other == mealWithDishes ||
                (other is SelectableMealWithDishes && mealWithDishes.meal.idMeal == other.mealWithDishes.meal.idMeal)
    }

}



