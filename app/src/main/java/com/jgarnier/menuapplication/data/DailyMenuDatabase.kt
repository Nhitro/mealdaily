package com.jgarnier.menuapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jgarnier.menuapplication.data.entity.*

@Database(entities = [Food::class, FoodLine::class, Meal::class, Recipe::class, RecipeIngredient::class, Shop::class], version = 1, exportSchema = false)
abstract class DailyMenuDatabase : RoomDatabase() {

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: DailyMenuDatabase? = null

        fun getDatabase(context: Context): DailyMenuDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DailyMenuDatabase::class.java,
                    "menu_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}