package com.jgarnier.menuapplication.data.repository

import com.jgarnier.menuapplication.data.dao.MealDao
import com.jgarnier.menuapplication.data.entity.Meal
import com.jgarnier.menuapplication.data.raw.MealSort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class MealRepository(private val mealDao: MealDao) {


    suspend fun insert(localDate: LocalDate, order: Int, mealSort: MealSort) =
        withContext(Dispatchers.IO) {
            mealDao.insert(
                Meal(
                    0,
                    localDate.year,
                    localDate.monthValue,
                    localDate.dayOfMonth,
                    order,
                    mealSort
                )
            )
        }

    suspend fun getDailyMealAccording(localDate: LocalDate) = withContext(Dispatchers.IO) {
        mealDao.getMealWithDishesListAccordingDate(
            localDate.dayOfMonth,
            localDate.monthValue,
            localDate.year
        )
    }

    suspend fun getLastMealOrderOf(localDate: LocalDate) = withContext(Dispatchers.IO) {
        mealDao.getLastMealOrderOf(
            localDate.dayOfMonth,
            localDate.monthValue,
            localDate.year
        )
    }

}