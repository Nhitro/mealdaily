package com.jgarnier.menuapplication.data.repository

import com.jgarnier.menuapplication.data.dao.DishDao
import com.jgarnier.menuapplication.data.entity.Dish
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DishRepository(private val dishDao: DishDao) {

    suspend fun insertDish(dish: Dish) = withContext(Dispatchers.IO) {
        dishDao.insertDish(dish)
    }

    suspend fun getDishesAssociatedToMeal(idMeal: Int) = withContext(Dispatchers.IO) {
        dishDao.getDishesAssociatedToMeal(idMeal)
    }

    suspend fun findDishesWithLineMatching(word: String) = withContext(Dispatchers.IO) {
        dishDao.getDishesWithLinesMatching("%".plus(word).plus("%"))
    }

}
