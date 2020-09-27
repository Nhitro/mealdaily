package com.jgarnier.menuapplication.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.jgarnier.menuapplication.data.entity.MealWithDishes
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Transaction
    @Query("SELECT * FROM Meal WHERE mealDay = :day AND mealMonth = :month AND mealYear = :year")
    fun getMealWithDishesListAccordingDate(day: Int, month: Int, year: Int) : Flow<List<MealWithDishes>>

}