package com.jgarnier.menuapplication.ui.tab_planning

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import by.kirich1409.viewbindingdelegate.viewBinding
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.data.Result
import com.jgarnier.menuapplication.databinding.FragmentPlanningBinding
import com.jgarnier.menuapplication.ui.base.BottomNavigationBarManager
import com.jgarnier.menuapplication.ui.base.TransitionFragment
import com.jgarnier.menuapplication.ui.base.setLocalDate
import com.jgarnier.menuapplication.ui.tab_planning.PlanningViewModel.Companion.CALENDAR_VIEW
import com.jgarnier.menuapplication.ui.tab_planning.meals.MealsAdapter
import com.jgarnier.menuapplication.ui.tab_planning.meals.MealsMoveCallback
import com.jgarnier.menuapplication.ui.tab_planning.meals.SelectableMealWithDishes
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Update the motion layout state
        mViewModel.currentTypeView.value?.apply {
            mBinding.planningLayout.setTransition(
                    if (this == CALENDAR_VIEW) {
                        R.id.monthToDeleteMeal
                    } else {
                        R.id.dayToMonth
                    }
            )
        }

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

        mBinding.planningLayout.addTransitionListener(createOnTransitionFabChange())

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
            navigate(action)
        }

        // View Model observation
        mViewModel.selectedLocalDate.observe(viewLifecycleOwner, observeSelectedLocalDate(adapter))
        mViewModel.currentTypeView.observe(viewLifecycleOwner, observeCurrentTypeView())
        mViewModel.fetchedData.observe(viewLifecycleOwner, observeMealWithDishesResult())
        mViewModel.isDeletingMode.observe(viewLifecycleOwner, observeIsDeletingMode())
        mViewModel.mealSelectedNumber.observe(viewLifecycleOwner, observeSelectedNumber())
    }

    private fun createOnTransitionFabChange(): MotionLayout.TransitionListener {
        return object : MotionLayout.TransitionListener {

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
                // empty
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                // empty
            }

            override fun onTransitionStarted(motionLayout: MotionLayout?, startTransition: Int, endTransition: Int) {
                if (activity is BottomNavigationBarManager) {
                    val bottomManager = (activity as BottomNavigationBarManager)

                    if (bottomManager.isShown() && (endTransition == R.id.day_to_delete_set || endTransition == R.id.month_to_delete_set)) {
                        bottomManager.changeBottomVisibility(false)
                    } else if (!bottomManager.isShown() && (startTransition == R.id.day_constraint || startTransition == R.id.month_constraint)) {
                        bottomManager.changeBottomVisibility(true)
                    }
                }
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, constraintId: Int) {
                if ((constraintId == R.id.day_constraint || constraintId == R.id.month_constraint)) {
                    mBinding.planningLayout.setTransition(R.id.dayToMonth)
                }
            }
        }
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
            // Managing transition calendar <- week days list
            with(mBinding.planningLayout) {
                if (it == CALENDAR_VIEW) {
                    setTransition(R.id.dayToMonth)
                    transitionToEnd()
                } else {
                    setTransition(R.id.dayToMonth)
                    transitionToStart()
                }
            }
        }
    }

    private fun observeMealWithDishesResult(): Observer<Result<List<SelectableMealWithDishes>>> {
        val adapter = MealsAdapter(userClickedOnMeal())
        val mealsMoveTouchHelper = ItemTouchHelper(MealsMoveCallback(adapter))
        mealsMoveTouchHelper.attachToRecyclerView(mBinding.dayMeals)
        mBinding.dayMeals.adapter = adapter

        return Observer {
            it?.apply {
                if (it is Result.Loading) {
                    mBinding.dayMealsLoader.visibility = View.VISIBLE
                    mBinding.dayMeals.visibility = View.INVISIBLE
                } else {
                    mBinding.dayMealsLoader.visibility = View.GONE

                    if (it.data != null && it.data.isNotEmpty()) {
                        mBinding.dayMealsInformation.visibility = View.GONE
                        mBinding.dayMeals.visibility = View.VISIBLE
                        adapter.submitList(it.data)
                    } else {
                        mBinding.dayMealsInformation.visibility = View.VISIBLE
                        mBinding.dayMeals.visibility = View.INVISIBLE
                        adapter.submitList(null)
                    }
                }
            }
        }
    }

    private fun observeIsDeletingMode() = Observer<Boolean> {
        with(mBinding.planningLayout) {
            if (it) {
                if (mBinding.planningDaysCalendar.isVisible) {
                    setTransition(R.id.monthToDeleteMeal)
                } else {
                    setTransition(R.id.dayToDeleteMeal)
                }
                transitionToEnd()
            } else {
                addTransitionListener(resetTransitionAfterBackToStart())
                transitionToStart()
            }

            (mBinding.dayMeals.adapter as? MealsAdapter)?.apply {
                this.onDeletingModeChange(it)
            }
        }
    }

    private fun resetTransitionAfterBackToStart(): MotionLayout.TransitionListener {
        return object : MotionLayout.TransitionListener {

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
                // empty
            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                // empty
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                // empty
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, p1: Int) {
                val listener = this
                with(mBinding.planningLayout) {
                    removeTransitionListener(listener)
                    setTransition(R.id.dayToMonth)
                }
            }

        }
    }

    /**
     * Navigate to meal detail on user click
     */
    private fun userClickedOnMeal(): Consumer<SelectableMealWithDishes> {
        return Consumer {
            if (it.isStateChanging) {
                mViewModel.userChangeStateOf(it)
            } else {
                it.mealWithDishes.meal.apply {
                    val action = PlanningFragmentDirections.actionPlanningFragmentToMealDetailFragment(
                            mealDay, mealMonth, mealYear, mealSort.name
                    )
                    navigate(action)
                }
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