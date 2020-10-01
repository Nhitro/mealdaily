package com.jgarnier.menuapplication.ui.tab_planning.meals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.data.entity.MealWithDishes
import java.time.LocalDate

class MealsAdapter : RecyclerView.Adapter<MealViewHolder>() {

    private val mMealWithDishes = ArrayList<MealWithDishes>()

    private var mSelectedDate: LocalDate? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.holder_day_meal, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.update(mMealWithDishes[position])
    }

    override fun getItemCount() = mMealWithDishes.size

    fun updateList(selectedDate: LocalDate?, newMealsWithDishes: List<MealWithDishes>?) {
        if (mMealWithDishes.isNotEmpty() && newMealsWithDishes.isNullOrEmpty()) {
            mMealWithDishes.clear()
            notifyDataSetChanged()
        } else if (newMealsWithDishes != null) {
            selectedDate?.apply {
                mMealWithDishes.clear()
                mMealWithDishes.addAll(newMealsWithDishes)
                if (this != mSelectedDate) {
                    notifyDataSetChanged()
                } else {
                    notifyItemInserted(mMealWithDishes.size - 1)
                }
                mSelectedDate = this
            }
        }
    }

}
