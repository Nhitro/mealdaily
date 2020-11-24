package com.jgarnier.menuapplication.ui.tab_planning.dishlines

import com.jgarnier.menuapplication.ui.base.AbstractMoveCallback
import com.jgarnier.menuapplication.ui.base.ItemTouchHelperContract

class DishLinesMoveCallback(adapter: ItemTouchHelperContract) : AbstractMoveCallback(adapter) {

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

}