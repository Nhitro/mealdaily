package com.jgarnier.menuapplication.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.jgarnier.menuapplication.data.raw.AmountSort

@Entity(foreignKeys = [
    ForeignKey(entity = Dish::class, parentColumns = ["idDish"], childColumns = ["dishParentId"], onDelete = ForeignKey.CASCADE)
])
data class DishLine(
        @PrimaryKey(autoGenerate = true) val idDishLine: Int,
        var dishParentId: Int,
        val foodName: String,
        val amount: Double,
        val amountSort: AmountSort
)