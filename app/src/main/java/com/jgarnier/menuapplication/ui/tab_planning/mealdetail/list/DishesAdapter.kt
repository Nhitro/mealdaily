package com.jgarnier.menuapplication.ui.tab_planning.mealdetail.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.data.entity.Dish

class DishesAdapter : ListAdapter<Dish, DishHolder>(DishDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishHolder {
        return DishHolder(LayoutInflater.from(parent.context).inflate(R.layout.holder_dish, parent, false))
    }

    override fun onBindViewHolder(holder: DishHolder, position: Int) {
        holder.update(getItem(position))
    }

}