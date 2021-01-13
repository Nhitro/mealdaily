package com.jgarnier.menuapplication.ui.base

import android.animation.Animator

/**
 * Utility class with the aim to avoid boiler plate when implementing [Animator.AnimatorListener]
 */
open class ExtendedAnimatorListener(
        private val onStartRunnable: Runnable? = Runnable {},
        private val onEndRunnable: Runnable? = Runnable {},
        private val onRepeatRunnable: Runnable? = Runnable {},
        private val onCancelRunnable: Runnable? = Runnable {}
) : Animator.AnimatorListener {

    override fun onAnimationRepeat(animation: Animator?) {
        onRepeatRunnable?.run()
    }

    override fun onAnimationEnd(animation: Animator?) {
        onEndRunnable?.run()
    }

    override fun onAnimationCancel(animation: Animator?) {
        onCancelRunnable?.run()
    }

    override fun onAnimationStart(animation: Animator?) {
        onStartRunnable?.run()
    }

}