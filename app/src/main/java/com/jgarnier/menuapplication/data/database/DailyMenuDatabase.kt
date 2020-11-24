package com.jgarnier.menuapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jgarnier.menuapplication.data.converter.Converters
import com.jgarnier.menuapplication.data.dao.DishDao
import com.jgarnier.menuapplication.data.dao.DishLineDao
import com.jgarnier.menuapplication.data.dao.MealDao
import com.jgarnier.menuapplication.data.entity.Dish
import com.jgarnier.menuapplication.data.entity.DishLine
import com.jgarnier.menuapplication.data.entity.Meal
import com.jgarnier.menuapplication.data.entity.MealDishCrossRef

@Database(entities = [Dish::class, DishLine::class, Meal::class, MealDishCrossRef::class], version = 1)
@TypeConverters(Converters::class)
abstract class DailyMenuDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao

    abstract fun dishDao(): DishDao

    abstract fun dishLineDao(): DishLineDao

}