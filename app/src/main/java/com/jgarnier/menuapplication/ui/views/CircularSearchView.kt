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

class CircularSearchView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    val mBinding: ViewSearchLayoutBinding =
            ViewSearchLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    var isSearchOpen = false

    init {
        mBinding.searchViewOpenSearch.setOnClickListener { openSearch() }
        mBinding.searchViewCloseSearch.setOnClickListener { closeSearch() }
        mBinding.searchViewInputText.addTextChangedListener { text ->
            mBinding.searchViewClearSearch.visibility =
                    if (text.isNullOrEmpty()) View.GONE
                    else View.VISIBLE
        }
        mBinding.searchViewClearSearch.setOnClickListener { mBinding.searchViewInputText.text.clear() }

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CircularSearchView)

        val id = attributes.getResourceId(R.styleable.CircularSearchView_toolbarTitle, -1)
        val title = attributes.getString(R.styleable.CircularSearchView_toolbarTitle)

        if (id != -1) {
            mBinding.searchViewTitle.setText(id)
        } else if (title != null) {
            mBinding.searchViewTitle.text = title
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
        mBinding.searchViewCloseSearch.setOnClickListener(null)

        val circularConceal = ViewAnimationUtils.createCircularReveal(
                mBinding.searchOpenView,
                (mBinding.searchViewOpenSearch.right + mBinding.searchViewOpenSearch.left) / 2,
                (mBinding.searchViewOpenSearch.top + mBinding.searchViewOpenSearch.bottom) / 2,
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
        mBinding.searchViewOpenSearch.setOnClickListener(null)
        mBinding.searchViewInputText.setText("")
        mBinding.searchOpenView.visibility = View.VISIBLE

        val circularReveal = ViewAnimationUtils.createCircularReveal(
                mBinding.searchOpenView,
                (mBinding.searchViewOpenSearch.right + mBinding.searchViewOpenSearch.left) / 2,
                (mBinding.searchViewOpenSearch.top + mBinding.searchViewOpenSearch.bottom) / 2,
                0f, width.toFloat()
        )

        circularReveal.addListener(
                ExtendedAnimatorListener(
                        null,
                        Runnable {
                            isSearchOpen = true

                            mBinding.searchViewCloseSearch.setOnClickListener { closeSearch() }
                            mBinding.searchViewInputText.requestFocus()

                            val keyboard: InputMethodManager? =
                                    getSystemService(context, InputMethodManager::class.java)
                            keyboard?.showSoftInput(mBinding.searchViewInputText, 0)
                        }
                )
        )
        circularReveal.duration = 300
        circularReveal.start()
    }

    private fun onCircularEndRunnable(circularConceal: Animator) = Runnable {
        isSearchOpen = false
        mBinding.searchOpenView.visibility = View.GONE
        mBinding.searchViewInputText.setText("")
        mBinding.searchViewOpenSearch.setOnClickListener { openSearch() }
        circularConceal.removeAllListeners()

        val keyboard: InputMethodManager? =
                getSystemService(context, InputMethodManager::class.java)
        keyboard?.hideSoftInputFromWindow(mBinding.searchViewInputText.windowToken, 0)
    }
}