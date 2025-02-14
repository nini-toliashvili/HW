package com.example.test7.presentation

import androidx.viewpager2.widget.ViewPager2

class MarginPageTransformer(private val margin: Int) : ViewPager2.PageTransformer {
    override fun transformPage(page: android.view.View, position: Float) {

        val offset = position * -margin
        page.translationX = offset
    }
}
