package com.jgarnier.menuapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.jgarnier.menuapplication.data.entity.Meal
import com.jgarnier.menuapplication.data.entity.MealDishCrossRef
import com.jgarnier.menuapplication.data.entity.MealWithDishes
import com.jgarnier.menuapplication.data.raw.MealSort
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Insert
    fun insert(meal: Meal)

    @Insert
    fun insert(mealDishCrossRef: MealDishCrossRef)

    @Transaction
    @Query("SELECT * FROM Meal WHERE mealDay = :day AND mealMonth = :month AND mealYear = :year")
    fun getMealWithDishesListAccordingDate(
        day: Int,
        month: Int,
        year: Int
    ): Flow<List<MealWithDishes>>

    @Transaction
    @Query("SELECT * FROM Meal WHERE mealDay = :day AND mealMonth = :month AND mealYear = :year AND mealSort = :mealSort")
    fun getMealWithDishesAccordingDate(
            day: Int,
            month: Int,
            year: Int,
            mealSort: MealSort
    ): MealWithDishes

    @Query("SELECT MAX(Meal.mealOrder) FROM Meal WHERE mealDay = :day AND mealMonth = :month AND mealYear = :year")
    fun getLastMealOrderOf(day: Int, month: Int, year: Int): Int?

}