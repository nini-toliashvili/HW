package com.example.test7.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.test7.presentation.fragments.FragmentHome
import com.example.test7.presentation.fragments.FragmentLikes
import com.example.test7.presentation.fragments.FragmentMessages

class FragmentPageAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentLikes()   // First tab fragment
            1 -> FragmentHome()   // Second tab fragment
            2 -> FragmentMessages() // Third tab fragment
            else -> FragmentHome() // Default case
        }
    }

}