package com.jgarnier.menuapplication.ui.tab_planning.mealdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.databinding.FragmentDetailMenuBinding
import com.jgarnier.menuapplication.ui.base.TransitionFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuDetailFragment : TransitionFragment(R.layout.fragment_detail_menu) {

    private val mViewModel: MealDetailViewModel by viewModels()

    private val mBinding: FragmentDetailMenuBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
