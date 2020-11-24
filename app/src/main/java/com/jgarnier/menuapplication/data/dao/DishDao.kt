package com.jgarnier.menuapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.jgarnier.menuapplication.data.entity.Dish
import com.jgarnier.menuapplication.data.entity.DishWithLines
import kotlinx.coroutines.flow.Flow

@Dao
interface DishDao {

    @Insert
    fun insertDish(dish: Dish): Long

    @Transaction
    @Query("SELECT * FROM Dish INNER JOIN MealDishCrossRef ON Dish.idDish = MealDishCrossRef.idDish WHERE MealDishCrossRef.idMeal = :idMeal")
    fun getDishesAssociatedToMeal(idMeal: Int): Flow<List<Dish>>

    @Transaction
    @Query("SELECT * FROM Dish INNER JOIN DishLine ON Dish.idDish = DishLine.idDishLine WHERE Dish.dishName LIKE :word OR Dish.dishDescription LIKE :word OR DishLine.foodName LIKE :word")
    fun getDishesWithLinesMatching(word: String): Flow<List<DishWithLines>>
}