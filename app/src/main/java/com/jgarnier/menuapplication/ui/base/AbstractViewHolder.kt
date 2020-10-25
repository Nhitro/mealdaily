package com.jgarnier.menuapplication.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 *
 */
abstract class AbstractViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun update(data: T)

}