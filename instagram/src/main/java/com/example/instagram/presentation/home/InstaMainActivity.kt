package com.example.instagram.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.instagram.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.io.File

class InstaMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insta_main)

        codeCacheDir.setReadOnly()

        val viewpager2 = findViewById<ViewPager2>(R.id.viewpager2)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val tabText = listOf("feed", "post", "profile")
        val tabIcons = listOf(R.drawable.ic_feed, R.drawable.ic_post, R.drawable.ic_profile)

        viewpager2.adapter = InstaViewPagerAdapter(this@InstaMainActivity)

        TabLayoutMediator(tabLayout, viewpager2) { tab, position ->
            tab.text = tabText[position]
            tab.setIcon(tabIcons[position])
        }.attach()

//        tabLayout.addOnTabSelectedListener()
    }
}