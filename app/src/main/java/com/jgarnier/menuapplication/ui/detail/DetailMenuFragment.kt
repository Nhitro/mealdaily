package com.jgarnier.menuapplication.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.databinding.DetailMenuFragmentBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DetailMenuFragment : Fragment() {

    val args: DetailMenuFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.detail_menu_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = DetailMenuFragmentBinding.bind(view)
        val localDate = LocalDate.of(args.selectedYear, args.selectedMonth, args.selectedDay)

        binding.detailMenuTest.text = localDate.format(DateTimeFormatter.ofPattern("EEEE dd MMMM YYYY"))
    }
}