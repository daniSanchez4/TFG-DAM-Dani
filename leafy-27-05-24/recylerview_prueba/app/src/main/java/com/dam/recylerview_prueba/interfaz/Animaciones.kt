package com.dam.recylerview_prueba.interfaz

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView

interface Animaciones {

    fun animateViewWithSlideUp(view: View, duration: Long) {

        view.alpha = 0f
        view.translationY = 100f

        val fadeInAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        fadeInAnimator.duration = duration

        val translateYAnimator = ObjectAnimator.ofFloat(view, "translationY", 100f, 0f)
        translateYAnimator.duration = duration

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(fadeInAnimator, translateYAnimator)

        animatorSet.start()
    }

    fun animateImageView(imageView: ImageView) {

        imageView.scaleX = 0.5f
        imageView.scaleY = 0.5f

        val scaleXAnimator = ObjectAnimator.ofFloat(imageView, "scaleX", 1f)
        val scaleYAnimator = ObjectAnimator.ofFloat(imageView, "scaleY", 1f)

        scaleXAnimator.duration = 1000
        scaleYAnimator.duration = 1000

        scaleXAnimator.start()
        scaleYAnimator.start()
    }

    fun animateView(view: View) {
        view.alpha = 0f
        val animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        animator.duration = 1100
        animator.start()
    }

}