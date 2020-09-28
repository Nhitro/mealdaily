package com.jgarnier.menuapplication.ui.tab_planning.mealdialog

import android.app.Dialog
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.data.raw.MealSort
import com.jgarnier.menuapplication.databinding.FragmentMealDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealDialogFragment : DialogFragment() {

    private val mArgs: MealDialogFragmentArgs by navArgs()

    private val mViewModel: MealDialogViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { activity ->
            val builder = MaterialAlertDialogBuilder(activity, R.style.AlertDialogTheme)
            val inflater = requireActivity().layoutInflater

            val rootView = inflater.inflate(R.layout.fragment_meal_dialog, null)
            val databinding = FragmentMealDialogBinding.bind(rootView)

            MealSort.values().map {
                databinding.mealDialogRadioGroup.addView(createRadioButton(mealSort = it))
            }

            databinding.mealDialogRadioGroup.check(MealSort.BREAK_FAST.ordinal)

            builder.setTitle(R.string.meal_dialog_title)
            builder.setView(rootView)
            builder.setPositiveButton(R.string.dialog_validate_label) { _, _ ->
                mViewModel.insert(
                    mArgs.selectedDate
                )
            }
            builder.setNegativeButton(R.string.dialog_cancel_label) { _, _ -> dismiss() }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun createRadioButton(mealSort: MealSort): RadioButton {
        val mealsArray = resources.getStringArray(R.array.meals_array)
        val radioButton = RadioButton(requireContext())

        radioButton.id = mealSort.ordinal

        radioButton.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        radioButton.text = when (mealSort) {
            MealSort.BREAK_FAST -> mealsArray[0]
            MealSort.LUNCH -> mealsArray[1]
            MealSort.DINNER -> mealsArray[3]
            MealSort.SNACK -> mealsArray[2]
        }

        return radioButton
    }

}