package com.jgarnier.menuapplication.ui.tab_planning.dishessearch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.data.Result
import com.jgarnier.menuapplication.data.entity.DishWithLines
import com.jgarnier.menuapplication.databinding.FragmentDishesSearchBinding
import com.jgarnier.menuapplication.ui.base.TransitionFragment
import com.jgarnier.menuapplication.ui.tab_planning.dishessearch.list.DishWithLineAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class DishesSearchFragment : TransitionFragment(R.layout.fragment_dishes_search) {

    private val mViewModel: DishesSearchViewModel by viewModels()

    private val mBinding: FragmentDishesSearchBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.dishAddFab.setOnClickListener {
            val action = DishesSearchFragmentDirections.actionDishesSearchFragmentToDishCreationFragment()
            findNavController().navigate(action)
        }

        val adapter = DishWithLineAdapter()
        mBinding.dishesSearchList.adapter = adapter

        mBinding.dishesSearchList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (mBinding.dishesToolbar.isSearchOpen
                        && mBinding.dishesToolbar.isSearchTextIsEmpty()
                        && newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    mBinding.dishesToolbar.closeSearch()
                }
            }
        })

        mBinding.dishesToolbar.addOnTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // empty
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                mViewModel.updateSearchFilter(text)
            }

        })

        mViewModel.fetchedData.observe(viewLifecycleOwner, Observer { onFetchDataChanged(adapter, it) })
    }

    private fun onFetchDataChanged(adapter: DishWithLineAdapter, result: Result<List<DishWithLines>>) {
        if (result is Result.Loading) {

        } else {
            adapter.submitList(result.data)
        }
    }
}