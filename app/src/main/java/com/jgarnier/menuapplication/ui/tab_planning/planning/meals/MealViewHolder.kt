package com.jgarnier.menuapplication.ui.tab_planning.planning.meals

import android.view.View
import com.bumptech.glide.Glide
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.data.entity.Dish
import com.jgarnier.menuapplication.data.raw.MealSort
import com.jgarnier.menuapplication.databinding.HolderDayMealBinding
import com.jgarnier.menuapplication.ui.base.AbstractViewHolder
import com.jgarnier.menuapplication.ui.base.SelectableContract
import java.util.function.Consumer

class MealViewHolder(
        private val mUserClickedOnMeal: Consumer<SelectableMealWithDishes>,
        itemView: View
) : AbstractViewHolder<SelectableMealWithDishes>(itemView) {

    private val mBinding = HolderDayMealBinding.bind(itemView)

    override fun update(data: SelectableMealWithDishes, selectableManager: SelectableContract) {
        val context = mBinding.root.context
        val dishes = data.mealWithDishes.dishes
        val mealSort = data.mealWithDishes.meal.mealSort

        Glide.with(mBinding.root)
                .load(iconAccordingMealSort(mealSort))
                .into(mBinding.dayMealIcon.frontLayout.findViewById(R.id.day_meal_front_icon))

        manageFlipViewAccording(data.isSelected)
        manageViewForDishes(dishes)

        mBinding.dayMealName.text = context.getText(textAccordingMealSort(mealSort))

        mBinding.dayMealIcon.setOnClickListener {
            mUserClickedOnMeal.accept(SelectableMealWithDishes(data.mealWithDishes, !mBinding.dayMealIcon.isFlipped, true))
            mBinding.dayMealIcon.showNext()
        }

        mBinding.dayMealContainer.setOnClickListener {
            val isSelectable = selectableManager.isSelectable()
            mUserClickedOnMeal.accept(SelectableMealWithDishes(data.mealWithDishes, !mBinding.dayMealIcon.isFlipped, isSelectable))
            if (isSelectable) {
                mBinding.dayMealIcon.showNext()
            }
        }
    }

    private fun manageFlipViewAccording(isSelected: Boolean) {
        with(mBinding.dayMealIcon) {
            if (isSelected != isFlipped) {
                flipSilently(isSelected)
            }
        }
    }

    private fun manageViewForDishes(dishes: List<Dish>) {
        if (dishes.isNotEmpty()) {
            mBinding.dayMealFirstMenuName.visibility = View.VISIBLE
            mBinding.dayMealFirstMenuName.text = dishes[0].dishName

            val secondMenuName = mBinding.dayMealSecondMenuName
            if (dishes.size > 2) {
                secondMenuName.text = dishes[1].dishName
                secondMenuName.visibility = View.VISIBLE
            } else {
                secondMenuName.visibility = View.GONE
            }
        } else {
            mBinding.dayMealFirstMenuName.visibility = View.GONE
            mBinding.dayMealSecondMenuName.visibility = View.GONE
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