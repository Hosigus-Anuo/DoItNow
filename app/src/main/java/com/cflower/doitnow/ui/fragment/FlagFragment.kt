package com.cflower.doitnow.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.cflower.doitnow.R
import com.cflower.doitnow.ui.activity.HabitActivity
import com.cflower.doitnow.ui.activity.TodayActivity
import com.cflower.doitnow.viewmodel.FlagViewModel
import com.cflower.lib_common.ui.BaseViewModelFragment
import kotlinx.android.synthetic.main.fragment_flag.*

class FlagFragment : BaseViewModelFragment<FlagViewModel>() {
    override val layoutRes: Int = R.layout.fragment_flag
    override val viewModelClass: Class<FlagViewModel> = FlagViewModel::class.java

    override fun View.onCreated(savedInstanceState: Bundle?) {
        setEvent()
    }

    private fun setEvent() {
        fab_add.setOnClickListener {
            if (rl_menu_content_flag.isVisible) {
                rl_menu_content_flag.isVisible = false
                fab_add.setImageResource(R.drawable.add_float)
            } else {
                rl_menu_content_flag.isVisible = true
                fab_add.setImageResource(R.drawable.flag_cancel)
            }
        }
        fab_today_flag.setOnClickListener {
            startActivity<TodayActivity>(true)
        }
        fab_habit_flag.setOnClickListener {
            startActivity<HabitActivity>(true)
        }
        fab_target_flag.setOnClickListener {
            viewModel.showError()
        }
        img_rank_flag.setOnClickListener {
            viewModel.showError()
        }
    }
}