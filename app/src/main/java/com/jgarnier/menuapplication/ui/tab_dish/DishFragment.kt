package com.jgarnier.menuapplication.ui.tab_dish

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.data.Result
import com.jgarnier.menuapplication.data.entity.DishWithLines
import com.jgarnier.menuapplication.databinding.FragmentDishesBinding
import com.jgarnier.menuapplication.ui.base.ExtendedTextWatcher
import com.jgarnier.menuapplication.ui.base.TransitionFragment
import com.jgarnier.menuapplication.ui.tab_planning.dishessearch.DishesSearchFragmentDirections
import com.jgarnier.menuapplication.ui.tab_planning.dishessearch.list.DishWithLineAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.util.function.Consumer

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DishFragment : TransitionFragment(R.layout.fragment_dishes) {

    private val mViewModel: DishViewModel by viewModels()

    private val mBinding: FragmentDishesBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DishWithLineAdapter(Consumer { })
        mBinding.dishesSearchList.adapter = adapter

        redirectOnFabClick()
        closeSearchOnScrollChange()
        listenSearchTextChange()

        mViewModel.fetchedData.observe(
                viewLifecycleOwner,
                Observer { onFetchDataChanged(adapter, it) }
        )
    }

    private fun listenSearchTextChange() {
        mBinding.dishesToolbar.addOnTextChangedListener(
                ExtendedTextWatcher(
                        null,
                        null,
                        Consumer { mViewModel.updateSearchFilter(it.charSequence) }
                )
        )
    }

    private fun redirectOnFabClick() {
        mBinding.dishAddFab.setOnClickListener {
            val action =
                    DishesSearchFragmentDirections.actionDishesSearchFragmentToDishCreationFragment()
            findNavController().navigate(action)
        }
    }

    private fun closeSearchOnScrollChange() {
        mBinding.dishesSearchList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (mBinding.dishesToolbar.isSearchOpen
                        && mBinding.dishesToolbar.isSearchTextIsEmpty()
                        && newState == RecyclerView.SCROLL_STATE_DRAGGING
                ) {
                    mBinding.dishesToolbar.closeSearch()
                }
            }
        })
    }

    private fun onFetchDataChanged(
            adapter: DishWithLineAdapter,
            result: Result<List<DishWithLines>>
    ) {
        if (result is Result.Loading) {
            mBinding.dishLoading.visibility = View.VISIBLE
            mBinding.dishesSearchList.visibility = View.INVISIBLE
            mBinding.dishAddFab.hide()
        } else {
            adapter.submitList(result.data)
            mBinding.dishLoading.visibility = View.GONE
            mBinding.dishesSearchList.visibility = View.VISIBLE
            mBinding.dishAddFab.show()
        }
    }
}