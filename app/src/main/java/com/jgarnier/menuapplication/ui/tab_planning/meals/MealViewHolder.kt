package com.jgarnier.menuapplication.ui.tab_planning.meals

import android.view.View
import com.bumptech.glide.Glide
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.data.entity.MealWithDishes
import com.jgarnier.menuapplication.data.raw.MealSort
import com.jgarnier.menuapplication.databinding.HolderDayMealBinding
import com.jgarnier.menuapplication.ui.base.AbstractViewHolder
import java.util.function.Consumer

class MealViewHolder(
        private val mUserClickedOnMeal: Consumer<MealWithDishes>,
        itemView: View
) : AbstractViewHolder<MealWithDishes>(itemView) {

    private val mBinding = HolderDayMealBinding.bind(itemView)

    override fun update(data: MealWithDishes) {
        val context = mBinding.root.context
        val dishes = data.dishes
        val mealSort = data.meal.mealSort

        Glide.with(mBinding.root)
                .load(iconAccordingMealSort(mealSort))
                .into(mBinding.dayMealIcon)

        mBinding.dayMealContainer.setOnClickListener { mUserClickedOnMeal.accept(data) }
        mBinding.dayMealName.text = context.getText(textAccordingMealSort(mealSort))

        if (dishes.isNotEmpty()) {
            mBinding.dayMealFirstMenuName.text = dishes[0].dishName

            val secondMenuName = mBinding.dayMealSecondMenuName
            if (dishes.size > 2) {
                secondMenuName.text = dishes[1].dishName
                secondMenuName.visibility = View.VISIBLE
            } else {
                secondMenuName.visibility = View.GONE
            }
        }
    }

    private fun textAccordingMealSort(mealSort: MealSort): Int {
        return when (mealSort) {
            MealSort.BREAK_FAST -> R.string.meal_break_fast
            MealSort.LUNCH -> R.string.meal_lunch
            MealSort.DINNER -> R.string.meal_dinner
            MealSort.SNACK -> R.string.meal_snack
        }
    }

    private fun iconAccordingMealSort(mealSort: MealSort): Int {
        return when (mealSort) {
            MealSort.BREAK_FAST -> R.drawable.ic_meal_breakfast
            MealSort.LUNCH -> R.drawable.ic_meal_lunch
            MealSort.DINNER -> R.drawable.ic_meal_dinner
            MealSort.SNACK -> R.drawable.ic_meal_snackbar
        }
    }

}