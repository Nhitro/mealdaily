package com.jgarnier.menuapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jgarnier.menuapplication.data.converter.Converters
import com.jgarnier.menuapplication.data.dao.DishDao
import com.jgarnier.menuapplication.data.dao.MealDao
import com.jgarnier.menuapplication.data.entity.*

@Database(entities = [DailyMeal::class, Dish::class, Food::class, FoodLine::class, Meal::class, MealDishCrossRef::class, Recipe::class, RecipeIngredient::class, Shop::class], version = 1)
@TypeConverters(Converters::class)
abstract class DailyMenuDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao

    abstract fun dishDao(): DishDao

}