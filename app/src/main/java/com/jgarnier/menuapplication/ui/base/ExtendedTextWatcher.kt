package com.jgarnier.menuapplication.ui.base

import android.text.Editable
import android.text.TextWatcher
import java.util.function.Consumer

/**
 * Utility class with the aim to avoid boiler plate when using a [TextWatcher]
 */
class ExtendedTextWatcher(
        private val mAfterTextChangedConsumer: Consumer<AfterTextChangedContainer>?,
        private val mBeforeTextChangedConsumer: Consumer<BeforeTextChangedContainer>?,
        private val mOnTextChangedConsumer: Consumer<OnTextChangedContainer>?
) : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        mAfterTextChangedConsumer?.accept(AfterTextChangedContainer(s))
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        mBeforeTextChangedConsumer?.accept(BeforeTextChangedContainer(s, start, count, after))
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        mOnTextChangedConsumer?.accept(OnTextChangedContainer(s, start, before, count))
    }

    data class AfterTextChangedContainer(val editable: Editable?)

    data class BeforeTextChangedContainer(
            val charSequence: CharSequence?,
            val start: Int,
            val count: Int,
            val after: Int
    )

    data class OnTextChangedContainer(
            val charSequence: CharSequence?,
            val start: Int,
            val before: Int,
            val count: Int
    )
}