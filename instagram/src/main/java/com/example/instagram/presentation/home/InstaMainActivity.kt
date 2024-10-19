package com.example.instagram.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import androidx.viewpager2.widget.ViewPager2
import com.example.instagram.R
import com.example.instagram.presentation.home.feed.InstaFeedFragment
import com.example.instagram.presentation.home.post.InstaPostFragment
import com.example.instagram.presentation.home.profile.InstaProfileFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.io.File

// tabLayout vs bottom navigation view
class InstaMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insta_main)

        val fragments = listOf(InstaFeedFragment(), InstaPostFragment(), InstaProfileFragment())

        codeCacheDir.setReadOnly()

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val tabText = listOf("feed", "post", "profile")
        val tabIcons = listOf(R.drawable.ic_feed, R.drawable.ic_post, R.drawable.ic_profile)

        tabLayout.addTab(tabLayout.newTab().setText(tabText[0]).setIcon(tabIcons[0]))
        tabLayout.addTab(tabLayout.newTab().setText(tabText[1]).setIcon(tabIcons[1]))
        tabLayout.addTab(tabLayout.newTab().setText(tabText[2]).setIcon(tabIcons[2]))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, fragments[0])
                            .setReorderingAllowed(true)
                            .commit()
                    }
                    1 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, fragments[1])
                            .setReorderingAllowed(true)
                            .commit()
                    }
                    2 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, fragments[2])
                            .setReorderingAllowed(true)
                            .commit()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) { }

            override fun onTabReselected(tab: TabLayout.Tab?) { }

        })
    }
}