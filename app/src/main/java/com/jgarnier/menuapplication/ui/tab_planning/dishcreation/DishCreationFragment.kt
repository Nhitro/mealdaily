package com.jgarnier.menuapplication.ui.tab_planning.dishcreation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.databinding.FragmentDishCreationBinding
import com.jgarnier.menuapplication.ui.base.TransitionFragment
import com.jgarnier.menuapplication.ui.tab_planning.dishlines.DishLinesAdapter

class DishCreationFragment : TransitionFragment(R.layout.fragment_dish_creation) {

    private val mViewModel: DishCreationViewModel by activityViewModels()

    private val mBinding: FragmentDishCreationBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dishLineAdapter = DishLinesAdapter()
        mBinding.dishCreationFoodList.adapter = dishLineAdapter

        mViewModel.dishLinesLiveData.observe(viewLifecycleOwner, Observer {
            var emptyTextVisibility = View.GONE

            if (it.isEmpty()) {
                emptyTextVisibility = View.VISIBLE
            }

            mBinding.dishCreationEmptyList.visibility = emptyTextVisibility
            dishLineAdapter.updateDataSet(it)
        })

        mViewModel.closeLiveData.observe(viewLifecycleOwner, Observer {
            findNavController().popBackStack()
        })

        // Setup on click listeners
        setupOnClickListeners()
    }

    private fun setupOnClickListeners() {
        mBinding.dishCreationAddFood.setOnClickListener {
            val navArgs = DishCreationFragmentDirections.actionDishCreationFragmentToFoodCreationFragment()
            findNavController().navigate(navArgs)
        }

        mBinding.dishCreationToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        mBinding.dishCreationToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.dish_creation_save -> checkInputsBeforeSave()
                R.id.dish_creation_delete -> mViewModel.deleteDish()
            }
            true
        }
    }

    private fun checkInputsBeforeSave() {
        val nameInput = mBinding.dishCreationNameInput

        if (!nameInput.text.isNullOrBlank()) {
            mViewModel.saveDish(nameInput.text.toString(), mBinding.dishCreationLinkInput.text)
            nameInput.error = null
        } else {
            // TODO : Move this string to strings.xml
            nameInput.error = "Ce champ ne peut être vide"
        }
    }

}