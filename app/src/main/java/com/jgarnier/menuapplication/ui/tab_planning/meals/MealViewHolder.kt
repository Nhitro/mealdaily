package com.jgarnier.menuapplication.ui.tab_planning.meals

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.data.entity.MealWithDishes
import com.jgarnier.menuapplication.data.raw.MealSort
import com.jgarnier.menuapplication.databinding.HolderDayMealBinding
import java.util.function.Consumer

class MealViewHolder(
    private val mUserClickedOnMeal: Consumer<MealWithDishes>,
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val mBinding = HolderDayMealBinding.bind(itemView)

    fun update(mealWithDishes: MealWithDishes) {
        val context = mBinding.root.context
        val dishes = mealWithDishes.dishes
        val mealSort = mealWithDishes.meal.mealSort

        Glide.with(mBinding.root)
                .load(iconAccordingMealSort(mealSort))
                .into(mBinding.dayMealIcon)

        mBinding.dayMealContainer.setOnClickListener { mUserClickedOnMeal.accept(mealWithDishes) }
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


    private fun backgroundColorAccordingMealSort(mealSort: MealSort): Int {
        return when (mealSort) {
            MealSort.BREAK_FAST -> R.color.vert_poireau
            MealSort.LUNCH -> R.color.vert_pomme
            MealSort.SNACK -> R.color.jaune_citron
            MealSort.DINNER -> R.color.orange
        }
    }

}