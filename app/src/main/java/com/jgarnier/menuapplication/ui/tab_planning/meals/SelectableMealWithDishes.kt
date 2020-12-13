package com.jgarnier.menuapplication.ui.tab_planning.meals

import com.jgarnier.menuapplication.data.entity.MealWithDishes

data class SelectableMealWithDishes(
        val mealWithDishes: MealWithDishes,
        val isSelected: Boolean,
        val isStateChanging: Boolean
)



