package com.jgarnier.menuapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.jgarnier.menuapplication.data.entity.DishLine

@Dao
interface DishLineDao {

    @Insert
    fun insertDishLines(dishLines: List<DishLine>)

}