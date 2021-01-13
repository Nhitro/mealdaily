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
import com.jgarnier.menuapplication.ui.tab_planning.mealdetail.list.DishesAdapter
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

        mBinding.mealDetailAddFab.setOnClickListener {
            val mealWithDishes = mViewModel.mealWithDishes
            if (mealWithDishes != null) {
                val action =
                    MealDetailFragmentDirections.actionMealDetailFragmentToDishesSearchFragment(
                        mealWithDishes.meal.idMeal
                    )
                findNavController().navigate(action)
            }
        }

        mBinding.mealDetailToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val adapter = DishesAdapter()
        mBinding.mealDetailList.adapter = adapter

        mViewModel.fetchedData.observe(viewLifecycleOwner, Observer {
            if (it is Result.Loading) {
                mBinding.mealDetailLoader.visibility = View.VISIBLE
                mBinding.mealDetailList.visibility = View.INVISIBLE
                mBinding.mealDetailEmpty.visibility = View.GONE
            } else {
                val dishes = it.data ?: ArrayList()
                mBinding.mealDetailLoader.visibility = View.GONE
                mBinding.mealDetailList.visibility = View.VISIBLE

                if (dishes.isEmpty()) {
                    mBinding.mealDetailEmpty.visibility = View.VISIBLE
                } else {
                    mBinding.mealDetailEmpty.visibility = View.GONE
                }

                adapter.submitList(dishes)
            }
        })

        mViewModel.mealTitle.observe(viewLifecycleOwner, Observer { mBinding.mealDetailToolbar.title = it })
    }
}
