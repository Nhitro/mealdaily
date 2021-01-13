package com.jgarnier.menuapplication.data.converter

import androidx.room.TypeConverter
import com.jgarnier.menuapplication.data.raw.AmountSort
import com.jgarnier.menuapplication.data.raw.MealSort
import java.util.*

/**
 * Converts any type of value that does not exist in SQLite to a SQLite value type and vice versa
 */
class Converters {

    @TypeConverter
    fun fromAmountSort(amountSort: AmountSort): String {
        return amountSort.name
    }

    @TypeConverter
    fun toAmountSort(name: String): AmountSort {
        return AmountSort.valueOf(name)
    }

    @TypeConverter
    fun fromMealSort(mealSort: MealSort): String {
        return mealSort.name
    }

    @TypeConverter
    fun toMealSort(name: String): MealSort {
        return MealSort.valueOf(name)
    }

    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }

}