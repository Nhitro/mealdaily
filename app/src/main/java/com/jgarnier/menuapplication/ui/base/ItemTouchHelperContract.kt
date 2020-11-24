package com.jgarnier.menuapplication.ui.base

import androidx.recyclerview.widget.RecyclerView

interface ItemTouchHelperContract {

    fun onRowDismiss(position: Int) {
        // Classes inheriting this contract must implement only what they need
    }

    fun onRowMoved(fromPosition: Int, toPosition: Int) {
        // Classes inheriting this contract must implement only what they need
    }

    fun onRowSelected(viewHolder: RecyclerView.ViewHolder) {
        // Classes inheriting this contract must implement only what they need
    }

    fun onRowClear(viewHolder: RecyclerView.ViewHolder) {
        // Classes inheriting this contract must implement only what they need
    }

}