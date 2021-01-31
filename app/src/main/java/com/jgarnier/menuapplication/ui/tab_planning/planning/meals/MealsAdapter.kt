package com.jgarnier.menuapplication.ui.tab_planning.planning.meals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.ui.base.ItemTouchHelperContract
import com.jgarnier.menuapplication.ui.base.SelectableContract
import java.util.*
import java.util.function.Consumer
import kotlin.collections.ArrayList

class MealsAdapter(
        private val mUserClickedOnMeal: Consumer<SelectableMealWithDishes>
) :
        ListAdapter<SelectableMealWithDishes, MealViewHolder>(MealsDiffUtils()),
        ItemTouchHelperContract,
        SelectableContract {

    private var mIsDeletingMode: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
                mUserClickedOnMeal,
                LayoutInflater.from(parent.context).inflate(R.layout.holder_day_meal, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.update(getItem(position), this)
    }

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        val mealWithDishes = ArrayList(currentList)
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(mealWithDishes, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(mealWithDishes, i, i - 1)
            }
        }
        submitList(mealWithDishes)
    }

    fun onDeletingModeChange(isDeletingMode: Boolean) {
        mIsDeletingMode = isDeletingMode
    }

    override fun isSelectable(): Boolean {
        return mIsDeletingMode
    }
}