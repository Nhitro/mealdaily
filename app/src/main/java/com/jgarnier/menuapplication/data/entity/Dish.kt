package com.jgarnier.menuapplication.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Dish(
        @PrimaryKey(autoGenerate = true) val idDish: Int,
        val dishName: String,
        val dishDescription: String?
)

data class DishWithLines(
        @Embedded val dish: Dish,
        @Relation(
                parentColumn = "idDish",
                entityColumn = "dishParentId"
        )
        val dishLines: List<DishLine>
)