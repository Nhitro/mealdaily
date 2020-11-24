package com.jgarnier.menuapplication.ui.tab_planning.dishlines

import android.view.View
import com.jgarnier.menuapplication.data.entity.DishLine
import com.jgarnier.menuapplication.databinding.HolderDishLineBinding
import com.jgarnier.menuapplication.ui.base.AbstractViewHolder

class DishLineViewHolder(itemView: View) : AbstractViewHolder<DishLine>(itemView) {

    private val mBinding = HolderDishLineBinding.bind(itemView)

    override fun update(data: DishLine) {
        val context = mBinding.root.context

        mBinding.dishLineName.text = data.foodName
        mBinding.dishLineAmount.text =
                data.amount
                        .toString()
                        .plus(" ")
                        .plus(context.getString(data.amountSort.shortNameId))
    }

}