package com.jgarnier.menuapplication.ui.tab_planning.meals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.data.entity.MealWithDishes
import java.time.LocalDate
import java.util.*
import java.util.function.Consumer
import kotlin.collections.ArrayList


class MealsAdapter(private val mUserClickedOnMeal: Consumer<MealWithDishes>) :
    RecyclerView.Adapter<MealViewHolder>(),
    MealsMoveCallback.ItemTouchHelperContract {

    private val mMealWithDishes = ArrayList<MealWithDishes>()

    private var mSelectedDate: LocalDate? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            mUserClickedOnMeal,
            LayoutInflater.from(parent.context).inflate(R.layout.holder_day_meal, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.update(mMealWithDishes[position])
    }

    override fun getItemCount() = mMealWithDishes.size

    fun updateList(selectedDate: LocalDate?, newMealsWithDishes: List<MealWithDishes>?) {
        if (newMealsWithDishes.isNullOrEmpty()) {
            mMealWithDishes.clear()
            notifyDataSetChanged()
        } else {
            selectedDate?.apply {
                var needToUpdate = false

                if (newMealsWithDishes.size != mMealWithDishes.size) {
                    needToUpdate = true
                }

                mMealWithDishes.clear()
                mMealWithDishes.addAll(newMealsWithDishes)

                if (this != mSelectedDate) {
                    notifyDataSetChanged()
                } else if (needToUpdate) {
                    notifyItemInserted(mMealWithDishes.size - 1)
                }
                mSelectedDate = this
            }
        }
    }

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(mMealWithDishes, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(mMealWithDishes, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onRowSelected(myViewHolder: MealViewHolder?) {
        // TODO : emphasis the cell
    }

    override fun onRowClear(myViewHolder: MealViewHolder?) {
        // TODO : de-emphasis the cell and update database
    }

}
