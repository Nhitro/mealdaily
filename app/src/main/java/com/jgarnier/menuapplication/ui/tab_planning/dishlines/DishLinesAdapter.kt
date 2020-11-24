package com.jgarnier.menuapplication.ui.tab_planning.dishlines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.data.entity.DishLine
import com.jgarnier.menuapplication.ui.base.ItemTouchHelperContract

class DishLinesAdapter : RecyclerView.Adapter<DishLineViewHolder>(), ItemTouchHelperContract {

    private val dishLineList: MutableList<DishLine> = mutableListOf()

    override fun getItemCount() = dishLineList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishLineViewHolder {
        return DishLineViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.holder_dish_line, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DishLineViewHolder, position: Int) {
        holder.update(dishLineList[position])
    }

    fun addDishLine(dishLine: DishLine) {
        dishLineList.add(dishLine)
        notifyItemInserted(dishLineList.size - 1)
    }

    fun updateDataSet(dishLines: List<DishLine>) {
        dishLineList.clear()
        dishLineList.addAll(dishLines)
        notifyDataSetChanged()
    }

    override fun onRowDismiss(position: Int) {
        dishLineList.removeAt(position)
        notifyItemRemoved(position)
    }
}