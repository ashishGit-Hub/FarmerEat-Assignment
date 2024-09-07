package com.ashishpatel.softwarelab.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ashishpatel.softwarelab.screens.onboarding.FirstScreenFragment
import com.ashishpatel.softwarelab.screens.onboarding.SecondFragment
import com.ashishpatel.softwarelab.screens.onboarding.ThirdFragment

class ViewPagerAdapter(
    list : ArrayList<Fragment>,
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    private val fragmentList = list

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> FirstScreenFragment()
            1 -> SecondFragment()
            else -> ThirdFragment()
        }
    }
}