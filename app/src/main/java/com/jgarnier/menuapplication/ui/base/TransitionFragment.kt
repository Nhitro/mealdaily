package com.jgarnier.menuapplication.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment

/**
 * See https://stackoverflow.com/questions/56133621/how-to-animate-android-navigation-architecture-fragment-as-sliding-over-old-frag
 * Also https://stackoverflow.com/questions/13005961/fragmenttransaction-animation-to-slide-in-over-top
 */
abstract class TransitionFragment(layoutId: Int): Fragment(layoutId) {

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ViewCompat.setTranslationZ(view, parentFragmentManager.backStackEntryCount.toFloat())
    }
}