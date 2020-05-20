package com.cflower.doitnow.ui.activity

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.cflower.doitnow.R
import com.cflower.doitnow.ui.adapter.MainViewPagerAdapter
import com.cflower.doitnow.ui.fragment.ChatFragment
import com.cflower.doitnow.ui.fragment.MineFragment
import com.cflower.doitnow.ui.fragment.SquareFragment
import com.cflower.doitnow.ui.fragment.TargetFragment
import com.cflower.lib_common.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override val resId: Int = R.layout.activity_main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(resId)
        setFragment()
        setEvent()
    }

    private fun setFragment() {
        val fmTarget = TargetFragment()
        val fmSquare = SquareFragment()
        val fmChat = ChatFragment()
        val fmMine = MineFragment()
        val list = listOf(fmTarget, fmSquare, fmChat, fmMine)
        vp_main.adapter = MainViewPagerAdapter(list, supportFragmentManager)
    }

    private fun setEvent() {
        radio_group.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rdo_target -> vp_main.currentItem = 0
                R.id.rdo_square -> vp_main.currentItem = 1
                R.id.rdo_chat -> vp_main.currentItem = 2
                R.id.rdo_mine -> vp_main.currentItem = 3
            }
        }
        vp_main.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) =
                radio_group.check(radio_group.getChildAt(position).id)

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

}
