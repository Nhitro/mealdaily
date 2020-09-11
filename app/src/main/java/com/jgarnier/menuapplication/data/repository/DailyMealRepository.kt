package com.jgarnier.menuapplication.data.repository

import com.jgarnier.menuapplication.data.dao.DailyMealDao
import com.jgarnier.menuapplication.data.entity.DailyMeal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class DailyMealRepository(private val dailyMealDao: DailyMealDao) {

    suspend fun insert(dailyMeal: DailyMeal) {
        dailyMealDao.insert(dailyMeal)
    }

    suspend fun getDailyMealAccording(localDate: LocalDate) = withContext(Dispatchers.IO) { dailyMealDao.getDailyMealAccordingDate(localDate.dayOfMonth, localDate.monthValue, localDate.year) }

}