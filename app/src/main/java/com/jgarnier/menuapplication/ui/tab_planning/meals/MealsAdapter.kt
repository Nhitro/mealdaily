package com.jgarnier.menuapplication.ui.tab_planning.meals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.data.entity.MealWithDishes

class MealsAdapter(private val mealsWithDishes: List<MealWithDishes>) :
    RecyclerView.Adapter<MealViewHolder>() {

    override fun getItemCount() = mealsWithDishes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.holder_day_meal, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.update(mealsWithDishes[position])
    }

}