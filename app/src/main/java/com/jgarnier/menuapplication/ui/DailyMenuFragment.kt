package com.jgarnier.menuapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jgarnier.menuapplication.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.daily_menu_fragment.*

@AndroidEntryPoint
class DailyMenuFragment : Fragment() {

    companion object {
        fun newInstance() = DailyMenuFragment()
    }

    private val viewModel: DailyMenuViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.daily_menu_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}