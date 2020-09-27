package com.jgarnier.menuapplication.data.repository

import com.jgarnier.menuapplication.data.dao.MealDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class MealRepository(private val mealDao: MealDao) {

    suspend fun getDailyMealAccording(localDate: LocalDate) = withContext(Dispatchers.IO) { mealDao.getMealWithDishesListAccordingDate(localDate.dayOfMonth, localDate.monthValue, localDate.year) }

}