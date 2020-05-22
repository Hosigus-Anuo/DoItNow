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
        rg_menu_main.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rg_clock_main -> vp_main.currentItem = 0
                R.id.rb_square_main -> vp_main.currentItem = 1
                R.id.rb_flag_main -> vp_main.currentItem = 2
                R.id.rg_mine_main -> vp_main.currentItem = 3
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
                rg_menu_main.check(rg_menu_main.getChildAt(position).id)

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

}
