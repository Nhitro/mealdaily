package com.jgarnier.menuapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.jgarnier.menuapplication.data.entity.Meal
import com.jgarnier.menuapplication.data.entity.MealWithDishes
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Insert
    suspend fun insert(meal: Meal)

    @Transaction
    @Query("SELECT * FROM Meal WHERE mealDay = :day AND mealMonth = :month AND mealYear = :year")
    fun getMealWithDishesListAccordingDate(
        day: Int,
        month: Int,
        year: Int
    ): Flow<List<MealWithDishes>>

    @Query("SELECT MAX(Meal.mealOrder) FROM Meal WHERE mealDay = :day AND mealMonth = :month AND mealYear = :year")
    fun getLastMealOrderOf(day: Int, month: Int, year: Int): Int?

}