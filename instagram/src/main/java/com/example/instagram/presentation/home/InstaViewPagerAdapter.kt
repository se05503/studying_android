package com.example.instagram.presentation.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.instagram.presentation.home.feed.InstaFeedFragment
import com.example.instagram.presentation.home.post.InstaPostFragment
import com.example.instagram.presentation.home.profile.InstaProfileFragment

class InstaViewPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
    val fragments: List<Fragment>

    init {
        fragments = listOf(InstaFeedFragment(), InstaPostFragment(), InstaProfileFragment())
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}