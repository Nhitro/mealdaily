package com.jgarnier.menuapplication.data.entity

import androidx.room.*
import com.jgarnier.menuapplication.data.raw.MealSort

@Entity(indices = [Index("idMeal")])
data class Meal(
    @PrimaryKey(autoGenerate = true) val idMeal: Int,
    val mealYear: Int,
    val mealMonth: Int,
    val mealDay: Int,
    val mealOrder: Int,
    val mealSort: MealSort
)

@Entity(primaryKeys = ["idMeal","idDish"], indices = [Index("idDish")])
data class MealDishCrossRef(
    val idMeal: Int,
    val idDish: Int
)

data class MealWithDishes(
    @Embedded val meal: Meal,
    @Relation(
        parentColumn = "idMeal",
        entityColumn = "idDish",
        associateBy = Junction(MealDishCrossRef::class)
    )
    val dishes: List<Dish>
)