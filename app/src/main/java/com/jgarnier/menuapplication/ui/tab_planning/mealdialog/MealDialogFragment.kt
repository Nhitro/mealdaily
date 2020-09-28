package com.jgarnier.menuapplication.ui.tab_planning.mealdialog

import android.app.Dialog
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.data.raw.MealSort
import com.jgarnier.menuapplication.databinding.FragmentMealDialogBinding


class MealDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { activity ->
            val builder = MaterialAlertDialogBuilder(activity, R.style.AlertDialogTheme)
            val inflater = requireActivity().layoutInflater

            val rootView = inflater.inflate(R.layout.fragment_meal_dialog, null)
            val databinding = FragmentMealDialogBinding.bind(rootView)

            MealSort.values().map {
                databinding.dialogRadioGroup.addView(createRadioButton(mealSort = it))
            }

            builder.setTitle(R.string.meal_dialog_title)
            builder.setView(rootView)
            builder.setPositiveButton(R.string.dialog_validate_label, { dialog, which -> })
            builder.setNegativeButton(R.string.dialog_cancel_label, { dialog, which -> })

            val dialog = builder.create()
            dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun createRadioButton(mealSort: MealSort): RadioButton {
        val mealsArray = resources.getStringArray(R.array.meals_array)
        val radioButton = RadioButton(requireContext())

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