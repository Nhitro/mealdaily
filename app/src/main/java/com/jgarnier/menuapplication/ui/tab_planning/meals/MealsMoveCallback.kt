package com.jgarnier.menuapplication.ui.tab_planning.meals

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


class MealsMoveCallback(private val mAdapter: ItemTouchHelperContract) :
    ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
        // empty
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        mAdapter.onRowMoved(viewHolder.bindingAdapterPosition, target.bindingAdapterPosition)
        return true
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && viewHolder is MealViewHolder) {
            val myViewHolder: MealViewHolder? = viewHolder
            mAdapter.onRowSelected(myViewHolder)
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if (viewHolder is MealViewHolder) {
            val myViewHolder: MealViewHolder? = viewHolder
            mAdapter.onRowClear(myViewHolder)
        }
    }

    interface ItemTouchHelperContract {
        fun onRowMoved(fromPosition: Int, toPosition: Int)
        fun onRowSelected(myViewHolder: MealViewHolder?)
        fun onRowClear(myViewHolder: MealViewHolder?)
    }

}