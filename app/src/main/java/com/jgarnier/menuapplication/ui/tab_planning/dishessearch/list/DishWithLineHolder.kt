package com.jgarnier.menuapplication.ui.tab_planning.dishessearch.list

import android.view.View
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.data.entity.DishWithLines
import com.jgarnier.menuapplication.databinding.HolderDishBinding
import com.jgarnier.menuapplication.ui.base.AbstractViewHolder
import java.util.function.Consumer

class DishWithLineHolder(private val mOnClickConsumer: Consumer<DishWithLines>, itemView: View) :
    AbstractViewHolder<DishWithLines>(itemView) {

    private val mBinding = HolderDishBinding.bind(itemView)

    override fun update(data: DishWithLines) {
        mBinding.dishName.text = data.dish.dishName
        mBinding.root.setOnClickListener {
            mOnClickConsumer.accept(data)
        }

        val dishWithLinesList = data.dishLines

        if (dishWithLinesList.isNullOrEmpty()) {
            mBinding.dishIngredients.visibility = View.INVISIBLE
        } else {
            val ingredientText = dishWithLinesList.joinToString(", ") { dishLine -> dishLine.foodName }
            mBinding.dishIngredients.visibility = View.VISIBLE
            mBinding.dishIngredients.text = mBinding.root.context.getString(R.string.dish_cell_ingredient_label, ingredientText.substring(0, ingredientText.length))
        }
    }

}