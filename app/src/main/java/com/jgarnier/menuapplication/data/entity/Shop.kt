package com.jgarnier.menuapplication.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Shop(
    @PrimaryKey(autoGenerate = true) val idShop: Int,
    val shopName: String
)