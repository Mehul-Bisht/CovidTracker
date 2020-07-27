package com.example.covidtracker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(manager: FragmentActivity) : FragmentStateAdapter(manager) {

    override fun getItemCount(): Int {
        return 2

    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> GlobalFragment()
            1 -> CountryFragment()
            else -> GlobalFragment()
        }
    }

}