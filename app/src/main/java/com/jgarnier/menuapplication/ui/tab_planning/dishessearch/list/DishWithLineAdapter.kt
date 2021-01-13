package com.jgarnier.menuapplication.ui.tab_planning.dishessearch.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.data.entity.DishWithLines
import java.util.function.Consumer

class DishWithLineAdapter(private val mOnClickConsumer: Consumer<DishWithLines>) :
    ListAdapter<DishWithLines, DishWithLineHolder>(DishWithLineDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishWithLineHolder {
        return DishWithLineHolder(
            mOnClickConsumer,
            LayoutInflater.from(parent.context).inflate(R.layout.holder_dish, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DishWithLineHolder, position: Int) {
        holder.update(getItem(position))
    }

}