package com.jgarnier.menuapplication.ui.tab_planning.meals

import com.jgarnier.menuapplication.ui.base.AbstractMoveCallback
import com.jgarnier.menuapplication.ui.base.ItemTouchHelperContract

class MealsMoveCallback(adapter: ItemTouchHelperContract) : AbstractMoveCallback(adapter) {

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

}