package com.jgarnier.menuapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jgarnier.menuapplication.data.entity.DailyMeal
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyMealDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dailyMeal: DailyMeal)

    @Query("SELECT * FROM DailyMeal WHERE dailyMealDay = :day AND dailyMealMonth = :month AND dailyMealYear = :year")
    fun getDailyMealAccordingDate(day: Int, month: Int, year: Int) : Flow<DailyMeal?>

}