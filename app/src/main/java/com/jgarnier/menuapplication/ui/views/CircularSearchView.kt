package com.jgarnier.menuapplication.ui.views

import android.animation.Animator
import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.databinding.ViewSearchLayoutBinding
import com.jgarnier.menuapplication.ui.base.ExtendedAnimatorListener

/**
 *
 */
class CircularSearchView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    var isSearchOpen = false

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_search_layout, this, true)
        val binding = ViewSearchLayoutBinding.bind(view)
        binding.searchViewOpenSearch.setOnClickListener { openSearch() }
        binding.searchViewCloseSearch.setOnClickListener { closeSearch() }
        binding.searchViewInputText.addTextChangedListener { text ->
            binding.searchViewClearSearch.visibility =
                if (text.isNullOrEmpty()) View.GONE
                else View.VISIBLE
        }
        binding.searchViewClearSearch.setOnClickListener { binding.searchViewInputText.text.clear() }

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CircularSearchView)

        val id = attributes.getResourceId(R.styleable.CircularSearchView_toolbarTitle, -1)
        val title = attributes.getString(R.styleable.CircularSearchView_toolbarTitle)

        if (id != -1) {
            binding.searchViewTitle.setText(id)
        } else if (title != null) {
            binding.searchViewTitle.text = title
        }

        attributes.recycle()
    }

    fun isSearchTextIsEmpty() = findViewById<TextView>(R.id.search_view_input_text).text.isEmpty()

    fun addOnTextChangedListener(textWatcher: TextWatcher) {
        findViewById<TextView>(R.id.search_view_input_text).addTextChangedListener(textWatcher)
    }

    fun removeOnTextChangedListener(textWatcher: TextWatcher) {
        findViewById<TextView>(R.id.search_view_input_text).removeTextChangedListener(textWatcher)
    }

    fun closeSearch() {
        val binding = ViewSearchLayoutBinding.bind(rootView)
        binding.searchViewCloseSearch.setOnClickListener(null)

        val circularConceal = ViewAnimationUtils.createCircularReveal(
            binding.searchOpenView,
            (binding.searchViewOpenSearch.right + binding.searchViewOpenSearch.left) / 2,
            (binding.searchViewOpenSearch.top + binding.searchViewOpenSearch.bottom) / 2,
            width.toFloat(), 0f
        )

        circularConceal.duration = 300
        circularConceal.addListener(
                ExtendedAnimatorListener(
                        null,
                        onCircularEndRunnable(circularConceal)
                )
        )

        circularConceal.start()
    }

    private fun openSearch() {
        val binding = ViewSearchLayoutBinding.bind(rootView)

        binding.searchViewOpenSearch.setOnClickListener(null)
        binding.searchViewInputText.setText("")
        binding.searchOpenView.visibility = View.VISIBLE

        val circularReveal = ViewAnimationUtils.createCircularReveal(
            binding.searchOpenView,
            (binding.searchViewOpenSearch.right + binding.searchViewOpenSearch.left) / 2,
            (binding.searchViewOpenSearch.top + binding.searchViewOpenSearch.bottom) / 2,
            0f, width.toFloat()
        )

        circularReveal.addListener(
            ExtendedAnimatorListener(
                null,
                Runnable {
                    isSearchOpen = true

                    binding.searchViewCloseSearch.setOnClickListener { closeSearch() }
                    binding.searchViewInputText.requestFocus()

                    val keyboard: InputMethodManager? =
                        getSystemService(context, InputMethodManager::class.java)
                    keyboard?.showSoftInput(binding.searchViewInputText, 0)
                        }
                )
        )
        circularReveal.duration = 300
        circularReveal.start()
    }

    private fun onCircularEndRunnable(circularConceal: Animator) = Runnable {
        val binding = ViewSearchLayoutBinding.bind(rootView)

        isSearchOpen = false
        binding.searchOpenView.visibility = View.GONE
        binding.searchViewInputText.setText("")
        binding.searchViewOpenSearch.setOnClickListener { openSearch() }
        circularConceal.removeAllListeners()

        val keyboard: InputMethodManager? =
            getSystemService(context, InputMethodManager::class.java)
        keyboard?.hideSoftInputFromWindow(binding.searchViewInputText.windowToken, 0)
    }
}