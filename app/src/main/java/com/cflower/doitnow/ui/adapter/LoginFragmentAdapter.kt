package com.cflower.doitnow.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.cflower.doitnow.R
import com.cflower.doitnow.ui.fragment.LoginFragment
import com.cflower.doitnow.ui.fragment.RegisterFragment

class LoginFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var tabNameList = listOf<String>("登录", "注册")
    private var fragmentList = listOf<Fragment>(
        RegisterFragment(),
        LoginFragment()
    )

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = tabNameList.size

    override fun getPageTitle(position: Int): CharSequence? = tabNameList[position]

}