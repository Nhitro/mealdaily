package com.jgarnier.menuapplication.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 *
 */
abstract class AbstractViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    open fun update(data: T) {
        // emtpy
    }

    open fun update(data: T, selectableManager: SelectableContract) {
        // empty
    }

}