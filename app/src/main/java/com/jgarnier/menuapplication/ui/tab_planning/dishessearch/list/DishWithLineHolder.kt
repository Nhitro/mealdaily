package com.jgarnier.menuapplication.ui.tab_planning.dishessearch.list

import android.view.View
import com.jgarnier.menuapplication.data.entity.DishWithLines
import com.jgarnier.menuapplication.databinding.HolderDishBinding
import com.jgarnier.menuapplication.ui.base.AbstractViewHolder

class DishWithLineHolder(itemView: View) : AbstractViewHolder<DishWithLines>(itemView) {

    private val mBinding = HolderDishBinding.bind(itemView)

    override fun update(data: DishWithLines) {
        mBinding.dishName.text = data.dish.dishName
    }

}