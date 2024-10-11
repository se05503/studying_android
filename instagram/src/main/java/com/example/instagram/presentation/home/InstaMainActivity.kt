package com.example.instagram.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.instagram.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class InstaMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insta_main)
        val viewpager2 = findViewById<ViewPager2>(R.id.viewpager2)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        viewpager2.adapter = InstaViewPagerAdapter(this@InstaMainActivity)
        tabLayout.addTab(tabLayout.newTab().setText("feed").setIcon(R.drawable.ic_feed), 0, true)
        tabLayout.addTab(tabLayout.newTab().setText("post").setIcon(R.drawable.ic_post), 1, false)
        tabLayout.addTab(tabLayout.newTab().setText("profile").setIcon(R.drawable.ic_profile), 2, false)

//        TabLayoutMediator(tabLayout, viewpager2) { _, _ ->
//            // 무슨 처리를 해야하지? 이미 text 랑 icon 위에서 설정했는데
//        }.attach()
//        tabLayout.addOnTabSelectedListener()
    }
}