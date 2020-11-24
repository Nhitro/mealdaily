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
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.ui.base.ExtendedAnimatorListener
import kotlinx.android.synthetic.main.view_search_layout.view.*

/**
 *
 */
class CircularSearchView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    var isSearchOpen = false

    init {
        LayoutInflater.from(context).inflate(R.layout.view_search_layout, this, true)
        search_view_open_search.setOnClickListener { openSearch() }
        search_view_close_search.setOnClickListener { closeSearch() }
        search_view_input_text.addTextChangedListener { text ->
            search_view_clear_search.visibility =
                    if (text.isNullOrEmpty()) View.GONE
                    else View.VISIBLE
        }
        search_view_clear_search.setOnClickListener { search_view_input_text.text.clear() }

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CircularSearchView)

        val id = attributes.getResourceId(R.styleable.CircularSearchView_toolbarTitle, -1)
        val title = attributes.getString(R.styleable.CircularSearchView_toolbarTitle)

        if (id != -1) {
            search_view_title.setText(id)
        } else if (title != null) {
            search_view_title.text = title
        }

        attributes.recycle()
    }

    fun isSearchTextIsEmpty() = search_view_input_text.text.isEmpty()

    fun addOnTextChangedListener(textWatcher: TextWatcher) {
        search_view_input_text.addTextChangedListener(textWatcher)
    }

    fun removeOnTextChangedListener(textWatcher: TextWatcher) {
        search_view_input_text.removeTextChangedListener(textWatcher)
    }

    fun closeSearch() {
        search_view_close_search.setOnClickListener(null)

        val circularConceal = ViewAnimationUtils.createCircularReveal(
                search_open_view,
                (search_view_open_search.right + search_view_open_search.left) / 2,
                (search_view_open_search.top + search_view_open_search.bottom) / 2,
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
        search_view_open_search.setOnClickListener(null)
        search_view_input_text.setText("")
        search_open_view.visibility = View.VISIBLE

        val circularReveal = ViewAnimationUtils.createCircularReveal(
                search_open_view,
                (search_view_open_search.right + search_view_open_search.left) / 2,
                (search_view_open_search.top + search_view_open_search.bottom) / 2,
                0f, width.toFloat()
        )

        circularReveal.addListener(
                ExtendedAnimatorListener(
                        null,
                        Runnable {
                            isSearchOpen = true

                            search_view_close_search.setOnClickListener { closeSearch() }
                            search_view_input_text.requestFocus()

                            val keyboard: InputMethodManager? = getSystemService(context, InputMethodManager::class.java)
                            keyboard?.showSoftInput(search_view_input_text, 0)
                        }
                )
        )
        circularReveal.duration = 300
        circularReveal.start()
    }

    private fun onCircularEndRunnable(circularConceal: Animator) = Runnable {
        isSearchOpen = false
        search_open_view.visibility = View.GONE
        search_view_input_text.setText("")
        search_view_open_search.setOnClickListener { openSearch() }
        circularConceal.removeAllListeners()

        val keyboard: InputMethodManager? = getSystemService(context, InputMethodManager::class.java)
        keyboard?.hideSoftInputFromWindow(search_view_input_text.windowToken, 0)
    }
}