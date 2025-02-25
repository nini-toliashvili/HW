package com.example.homework24.presentation.user.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.homework24.presentation.user.fragment.HomeFragment
import com.example.homework24.presentation.user.fragment.LikesFragment
import com.example.homework24.presentation.user.fragment.MessagesFragment
import com.example.homework24.presentation.user.fragment.NotificationsFragment

class FragmentPageAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LikesFragment()
            1 -> HomeFragment()
            2 -> MessagesFragment()
            3 -> NotificationsFragment()
            else -> HomeFragment()
        }
    }

}