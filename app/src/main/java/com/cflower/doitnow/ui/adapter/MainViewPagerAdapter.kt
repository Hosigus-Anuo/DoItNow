package com.cflower.doitnow.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MainViewPagerAdapter(var fmList:List<Fragment>,fm:FragmentManager):FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int = fmList.size

    override fun getItem(position: Int): Fragment =fmList[position]

    override fun getPageTitle(position: Int): CharSequence? {
        return super.getPageTitle(position)
    }
}