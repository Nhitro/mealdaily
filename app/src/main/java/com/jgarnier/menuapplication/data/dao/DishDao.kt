package com.jgarnier.menuapplication.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.jgarnier.menuapplication.data.entity.Dish
import kotlinx.coroutines.flow.Flow

@Dao
interface DishDao {

    @Transaction
    @Query("SELECT * FROM Dish INNER JOIN MealDishCrossRef ON Dish.idDish = MealDishCrossRef.idDish WHERE MealDishCrossRef.idMeal = :idMeal")
    fun getDishesAssociatedToMeal(idMeal: Int): Flow<List<Dish>>

}