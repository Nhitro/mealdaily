package com.jgarnier.menuapplication.ui.tab_planning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.transition.TransitionManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.databinding.PlanningFragmentBinding
import com.jgarnier.menuapplication.ui.base.TransitionFragment
import com.jgarnier.menuapplication.ui.tab_planning.PlanningViewModel.Companion.CALENDAR_VIEW
import com.jgarnier.menuapplication.ui.tab_planning.recyclerview.WeekAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.function.Consumer

/**
 * This fragment is in charge of showing the meals according to a date within the calendar or the week days list
 */
@AndroidEntryPoint
class PlanningFragment : TransitionFragment(R.layout.planning_fragment) {

    private val mViewModel: PlanningViewModel by viewModels()

    private val mBinding: PlanningFragmentBinding by viewBinding()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.planning_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = WeekAdapter(LocalDate.now(), Consumer { mViewModel.userSelectedDate(it) })

        mBinding.planningDaysDates.adapter = adapter
        mBinding.planningDaysDates.scrollToPosition(adapter.localDateNowIndex)

        mBinding.planningCalendarButtonGroup.addOnButtonCheckedListener{ _, checkedId, isChecked ->
            if (checkedId == R.id.planning_week_button && isChecked) {
                mViewModel.selectWeekView()
            } else if (checkedId == R.id.planning_month_button && isChecked) {
                mViewModel.selectMonthView()
            }
        }

        // View Model observation
        mViewModel.mCurrentTypeView.observe(viewLifecycleOwner,
            Observer {
                with(mBinding.planningCalendarFlipper, {
                    TransitionManager.beginDelayedTransition(mBinding.planningLayout)
                    if (it == CALENDAR_VIEW && displayedChild != 1) {
                        showNext()
                    } else if (it == PlanningViewModel.WEEK_DAY_VIEW && displayedChild != 0) {
                        showPrevious()
                    }
                })
            }
        )
    }

}