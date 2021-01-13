package com.jgarnier.menuapplication.ui.tab_planning.dishcreation

import android.text.Editable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgarnier.menuapplication.data.entity.Dish
import com.jgarnier.menuapplication.data.entity.DishLine
import com.jgarnier.menuapplication.data.raw.AmountSort
import com.jgarnier.menuapplication.data.repository.DishLineRepository
import com.jgarnier.menuapplication.data.repository.DishRepository
import com.jgarnier.menuapplication.ui.base.SingleLiveEvent
import kotlinx.coroutines.launch

class DishCreationViewModel @ViewModelInject constructor(
        private val mDishRepository: DishRepository,
        private val mDishLineRepository: DishLineRepository
) : ViewModel() {

    private var currentAmountSort = AmountSort.GRAMME

    private val backSingleLiveEvent = SingleLiveEvent<Boolean>()

    private val dishLines = MutableLiveData<List<DishLine>>(mutableListOf())

    val dishLinesLiveData: LiveData<List<DishLine>>
        get() = dishLines

    val closeLiveData: LiveData<Boolean>
        get() = backSingleLiveEvent

    /**
     *
     */
    fun addDishLine(name: String, amount: String) {
        val futureLine = mutableListOf<DishLine>()
        futureLine.addAll(dishLines.value!!)
        futureLine.add(DishLine(0, 0, name, amount.toDouble(), currentAmountSort))
        dishLines.postValue(futureLine)
    }

    fun selectedAmountSort(amountSortIndex: Int) {
        currentAmountSort = AmountSort.values()[amountSortIndex]
    }

    fun saveDish(name: String, description: Editable?) {
        val dish = Dish(0, name, description?.toString())
        viewModelScope.launch {
            val id = mDishRepository.insertDish(dish)
            val dishLinesToSave = dishLines.value

            if (dishLinesToSave != null) {
                mDishLineRepository.insertDishLines(
                        dishLinesToSave.onEach { it.dishParentId = id.toInt() }
                )
            }

            backSingleLiveEvent.postValue(true)
        }
    }

    fun deleteDish() {
        // TODO JGR : Delete dish
    }

}