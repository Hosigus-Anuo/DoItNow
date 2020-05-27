package com.cflower.doitnow.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.cflower.doitnow.R
import com.cflower.doitnow.model.flag.Flag
import com.cflower.doitnow.ui.activity.HabitActivity
import com.cflower.doitnow.ui.activity.TodayActivity
import com.cflower.doitnow.ui.adapter.FlagRvAdapter
import com.cflower.doitnow.ui.widget.FlagRvDecoration
import com.cflower.doitnow.viewmodel.FlagViewModel
import com.cflower.lib_common.ui.BaseViewModelFragment
import kotlinx.android.synthetic.main.fragment_flag.*

class FlagFragment : BaseViewModelFragment<FlagViewModel>() {
    override val layoutRes: Int = R.layout.fragment_flag
    override val viewModelClass: Class<FlagViewModel> = FlagViewModel::class.java
    private val mAdapter = FlagRvAdapter()

    override fun View.onCreated(savedInstanceState: Bundle?) {
        setEvent()
        rv_flag.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            addItemDecoration(FlagRvDecoration(context, mAdapter))
        }

        viewModel.flags.observeNotNull {
            it.habit.forEach { f -> f.type = Flag.Type.HABIT }
            it.target.forEach { f -> f.type = Flag.Type.TARGET }
            mAdapter.appendData(it.today)
            mAdapter.appendData(it.habit)
            mAdapter.appendData(it.target)
        }

        viewModel.loadFlagList()
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