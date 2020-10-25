package com.jgarnier.menuapplication.ui.tab_planning.mealdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.data.Result
import com.jgarnier.menuapplication.databinding.FragmentMealDetailBinding
import com.jgarnier.menuapplication.ui.base.TransitionFragment
import com.jgarnier.menuapplication.ui.tab_planning.dishes.DishesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MealDetailFragment : TransitionFragment(R.layout.fragment_meal_detail) {

    private val mViewModel: MealDetailViewModel by viewModels()

    private val mBinding: FragmentMealDetailBinding by viewBinding()

    private val mArgs: MealDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.fetchMealAndDishes(mArgs.day, mArgs.month, mArgs.year, mArgs.mealSort)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DishesAdapter()

        mBinding.menuDetailTitle.setNavigationOnClickListener { findNavController().popBackStack() }
        mBinding.menuDetailList.adapter = adapter

        mViewModel.fetchedData.observe(viewLifecycleOwner, Observer {
            if (it is Result.Loading) {
                mBinding.menuDetailLoader.visibility = View.VISIBLE
                mBinding.menuDetailList.visibility = View.INVISIBLE
                mBinding.menuDetailEmpty.visibility = View.GONE
            } else {
                val dishes = it.data ?: ArrayList()
                mBinding.menuDetailLoader.visibility = View.GONE
                mBinding.menuDetailList.visibility = View.VISIBLE

                if (dishes.isEmpty()) {
                    mBinding.menuDetailEmpty.visibility = View.VISIBLE
                } else {
                    mBinding.menuDetailEmpty.visibility = View.GONE
                }

                adapter.submitList(dishes)
            }
        })

        mViewModel.mealTitle.observe(viewLifecycleOwner, Observer { mBinding.menuDetailTitle.title = it })
    }
}
