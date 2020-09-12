package com.jgarnier.menuapplication.ui.daily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.OnRebindCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionManager
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.databinding.DailyMenuFragmentBinding
import com.transitionseverywhere.ChangeText
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate


/**
 * Fragment is in charge of showing the menu list
 */
@AndroidEntryPoint
class DailyMenuFragment : Fragment() {

    private val mViewModel: DailyMenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.fetchMealAccording(LocalDate.now())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.daily_menu_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = DailyMenuFragmentBinding.bind(view)

        binding.apply {
            // Set databinding values
            lifecycleOwner = this@DailyMenuFragment
            vm = mViewModel
            state = mViewModel.dailyMenuState

            // Initialize listeners
            dailyMenuErrorRetry.setOnClickListener { mViewModel.retryFetchMeal() }
            dailyMenuCalendar.setOnDateChangeListener { _, year, month, dayOfMonth -> mViewModel.fetchMealAccording(LocalDate.of(year, month + 1, dayOfMonth))}

            dailyMenuEdit.setOnClickListener {
                mViewModel.selectedDate.apply {
                    val action = DailyMenuFragmentDirections.actionDailyMenuFragmentToDetailMenuFragment(dayOfMonth, monthValue, year)
                    findNavController().navigate(action)
                }
            }

            // Add callback in order to perform ChangeText() transition each time binding values change
            addOnRebindCallback(object : OnRebindCallback<DailyMenuFragmentBinding>() {
                override fun onPreBind(binding: DailyMenuFragmentBinding?): Boolean {
                    TransitionManager
                        .beginDelayedTransition(
                            binding?.dailyMenuCardContainer as ViewGroup,
                            ChangeText()
                                .setChangeBehavior(ChangeText.CHANGE_BEHAVIOR_OUT_IN)
                                .setDuration(100)
                        )
                    return super.onPreBind(binding)
                }
            })
        }

    }

}