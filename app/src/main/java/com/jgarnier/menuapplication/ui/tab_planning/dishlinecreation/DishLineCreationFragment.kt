package com.jgarnier.menuapplication.ui.tab_planning.dishlinecreation

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import by.kirich1409.viewbindingdelegate.viewBinding
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.data.raw.AmountSort
import com.jgarnier.menuapplication.databinding.FragmentDishLineCreationBinding
import com.jgarnier.menuapplication.ui.base.TransitionFragment
import com.jgarnier.menuapplication.ui.tab_planning.dishcreation.DishCreationViewModel
import com.jgarnier.menuapplication.ui.tab_planning.dishlines.DishLinesAdapter
import com.jgarnier.menuapplication.ui.tab_planning.dishlines.DishLinesMoveCallback

class DishLineCreationFragment : TransitionFragment(R.layout.fragment_dish_line_creation) {

    private val mBinding: FragmentDishLineCreationBinding by viewBinding()

    private val mViewModel: DishCreationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayAdapter =
                ArrayAdapter(
                        requireContext(),
                        R.layout.support_simple_spinner_dropdown_item,
                        AmountSort
                                .values()
                                .map { getString(it.shortNameId) }
                )

        mBinding.dishLineCreationAmountSortInput.setOnItemClickListener { _, _, position, _ -> mViewModel.selectedAmountSort(position) }
        mBinding.dishLineCreationAmountSortInput.setText(AmountSort.GRAMME.shortNameId)
        mBinding.dishLineCreationAmountSortInput.setAdapter(arrayAdapter)

        val dishLineAdapter = DishLinesAdapter()
        val dishLinesMoveCallback = ItemTouchHelper(DishLinesMoveCallback(dishLineAdapter))
        dishLinesMoveCallback.attachToRecyclerView(mBinding.dishLineCreationDishLineList)
        mBinding.dishLineCreationDishLineList.adapter = dishLineAdapter

        mViewModel.dishLinesLiveData.observe(viewLifecycleOwner, Observer { dishLineAdapter.updateDataSet(it) })

        mBinding.dishLineCreationDishLineAddButton.setOnClickListener {
            val nameValue = mBinding.dishLineCreationDishLineNameInput.text
            val amountValue = mBinding.dishLineCreationQuantityInput.text

            if (nameValue != null && amountValue != null && !nameValue.isNullOrEmpty() && !amountValue.isNullOrEmpty()) {
                mViewModel.addDishLine(nameValue.toString(), amountValue.toString())
                clearInputsAndFocusName()
            } else {
                manageEmptyInputs()
            }
        }

    }

    private fun clearInputsAndFocusName() {
        mBinding.dishLineCreationQuantityInput.setText("")
        mBinding.dishLineCreationDishLineNameInput.setText("")
        mBinding.dishLineCreationDishLineNameInput.requestFocus()
    }

    private fun manageEmptyInputs() {
        val nameValue = mBinding.dishLineCreationDishLineNameInput.text
        val amountValue = mBinding.dishLineCreationQuantityInput.text

        if (nameValue == null || nameValue.isNullOrBlank()) {
            mBinding.dishLineCreationDishLineNameLayout.isErrorEnabled = true
        }

        if (amountValue == null || amountValue.isNullOrBlank()) {
            mBinding.dishLineCreationDishLineQuantityLayout.isErrorEnabled = true
        }
    }
}