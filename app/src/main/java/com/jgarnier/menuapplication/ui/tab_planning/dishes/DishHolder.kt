package com.jgarnier.menuapplication.ui.tab_planning.dishes

import android.view.View
import com.jgarnier.menuapplication.data.entity.Dish
import com.jgarnier.menuapplication.databinding.HolderDishBinding
import com.jgarnier.menuapplication.ui.base.AbstractViewHolder

/**
 *
 */
class DishHolder(itemView: View) : AbstractViewHolder<Dish>(itemView) {

    private val mBinding = HolderDishBinding.bind(itemView)

    override fun update(data: Dish) {
        mBinding.dishName.text = data.dishName
    }

}