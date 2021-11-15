package com.example.githubuserrview

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity){

    override fun createFragment(position: Int): Fragment {
        return FollowFragment.newInstance(position + 1)
    }

    override fun getItemCount(): Int {
        return 2
    }
}