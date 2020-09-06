package com.jgarnier.menuapplication.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.jgarnier.menuapplication.data.entity.Meal
import java.util.*

@Dao
interface MealDao {

    @Query("SELECT * FROM Meal WHERE mealDate > :date LIMIT :limit")
    fun getMealAccordingAfterDate(date: Date, limit: Int) : List<Meal>

}