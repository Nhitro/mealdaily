package com.jgarnier.menuapplication.data.repository

import com.jgarnier.menuapplication.data.dao.DishDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DishRepository(private val dishDao: DishDao) {

    suspend fun getDishesAssociatedToMeal(idMeal: Int) = withContext(Dispatchers.IO) {
        dishDao.getDishesAssociatedToMeal(idMeal)
    }

}
