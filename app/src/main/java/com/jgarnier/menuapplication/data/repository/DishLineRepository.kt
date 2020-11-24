package com.jgarnier.menuapplication.data.repository

import com.jgarnier.menuapplication.data.dao.DishLineDao
import com.jgarnier.menuapplication.data.entity.DishLine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DishLineRepository(private val dishLineDao: DishLineDao) {

    suspend fun insertDishLines(dishLines: List<DishLine>) = withContext(Dispatchers.IO) {
        dishLineDao.insertDishLines(dishLines)
    }

}