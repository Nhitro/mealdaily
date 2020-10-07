package com.jgarnier.menuapplication.ui.tab_planning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.transition.MaterialFade
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.data.Result
import com.jgarnier.menuapplication.data.entity.MealWithDishes
import com.jgarnier.menuapplication.databinding.FragmentPlanningBinding
import com.jgarnier.menuapplication.setLocalDate
import com.jgarnier.menuapplication.ui.base.TransitionFragment
import com.jgarnier.menuapplication.ui.tab_planning.PlanningViewModel.Companion.CALENDAR_VIEW
import com.jgarnier.menuapplication.ui.tab_planning.meals.MealsAdapter
import com.jgarnier.menuapplication.ui.tab_planning.week.WeekAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.function.Consumer

/**
 * This fragment is in charge of showing the meals according to a date within the calendar or the week days list
 */
@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class PlanningFragment : TransitionFragment(R.layout.fragment_planning) {

    private val mViewModel: PlanningViewModel by viewModels()

    private val mBinding: FragmentPlanningBinding by viewBinding()

    private var mSelectedDate: LocalDate? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_planning, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            WeekAdapter(LocalDate.now(), Consumer { mViewModel.userSelectedDate(it.localDate) })

        mBinding.planningDaysDates.adapter = adapter

        // Listeners
        mBinding.planningCalendarButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (checkedId == R.id.planning_week_button && isChecked) {
                mViewModel.selectWeekView()
            } else if (checkedId == R.id.planning_month_button && isChecked) {
                mViewModel.selectMonthView()
            }
        }

        mBinding.planningCurrentDayLabel.setOnClickListener {
            mBinding.planningDaysDates.smoothScrollToPosition(adapter.getSelectedPosition())
            mBinding.planningDaysCalendar.setLocalDate(
                mSelectedDate ?: LocalDate.now(),
                animate = true,
                center = false
            )
        }

        mBinding.planningDaysCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            mViewModel.userSelectedDate(LocalDate.of(year, month + 1, dayOfMonth))
        }

        mBinding.planningFab.setOnClickListener {
            val action = PlanningFragmentDirections.actionPlanningFragmentToMealDialogFragment(
                mSelectedDate ?: LocalDate.now()
            )
            findNavController().navigate(action)
        }

        // View Model observation
        mViewModel.selectedLocalDate.observe(viewLifecycleOwner, observeSelectedLocalDate(adapter))
        mViewModel.currentTypeView.observe(viewLifecycleOwner, observeCurrentTypeView())
        mViewModel.mealWithDishes.observe(viewLifecycleOwner, observeMealWithDishesResult())
    }

    private fun observeSelectedLocalDate(adapter: WeekAdapter): Observer<LocalDate> {
        return Observer {
            mSelectedDate = it

            // Update list if needed
            adapter.selectDate(mBinding.planningDaysDates, it)
            // Update calendar view if needed
            mBinding.planningDaysCalendar.setLocalDate(it, animate = false, center = true)

            // Update text
            mBinding.planningCurrentDayLabel.text = getCapitalizeDate(it)
        }
    }

    private fun observeCurrentTypeView(): Observer<Int> {
        return Observer {
            with(mBinding.planningCalendarFlipper, {
                TransitionManager.beginDelayedTransition(mBinding.planningLayout)
                // Managing transition calendar <- week days list
                if (it == CALENDAR_VIEW && displayedChild != 1) {
                    showNext()
                } else if (it == PlanningViewModel.WEEK_DAY_VIEW && displayedChild != 0) {
                    showPrevious()
                }
            })
        }
    }

    private fun observeMealWithDishesResult(): Observer<Result<List<MealWithDishes>>> {
        val adapter = MealsAdapter(userClickedOnMeal())
        mBinding.dayMeals.adapter = adapter

        return Observer {
            it?.apply {
                if (it is Result.Loading) {
                    mBinding.dayMealsLoader.visibility = View.VISIBLE
                    mBinding.dayMeals.visibility = View.INVISIBLE
                } else {
                    mBinding.dayMealsLoader.visibility = View.GONE

                    if (it.data != null && it.data.isNotEmpty()) {
                        adapter.updateList(mSelectedDate, it.data)
                        mBinding.dayMealsInformation.visibility = View.GONE
                        mBinding.dayMeals.visibility = View.VISIBLE
                    } else {
                        mBinding.dayMeals.visibility = View.GONE
                        mBinding.dayMealsInformation.visibility = View.VISIBLE
                        adapter.updateList(mSelectedDate, null)
                    }

                    TransitionManager.beginDelayedTransition(
                        mBinding.planningLayout,
                        MaterialFade()
                    )
                }
            }
        }
    }

    /**
     * Navigate to meal detail on user click
     */
    private fun userClickedOnMeal(): Consumer<MealWithDishes> {
        return Consumer {
            it.meal.apply {
                val action = PlanningFragmentDirections.actionPlanningFragmentToMenuDetailFragment(
                    mealDay, mealMonth, mealYear, mealSort.name
                )

                findNavController().navigate(action)
            }
        }
    }

    /**
     * @return localDate into "EEEE dd MMMM" format and capitalize each word (exp : Tuesday 14 September)
     */
    private fun getCapitalizeDate(localDate: LocalDate): String {
        return localDate.format(DateTimeFormatter.ofPattern("EEEE dd MMM"))
            .split(" ")
            .joinToString(" ") { it.capitalize() }
    }

}