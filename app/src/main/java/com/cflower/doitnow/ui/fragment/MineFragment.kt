package com.cflower.doitnow.ui.fragment

import android.os.Bundle
import android.view.View
import com.cflower.doitnow.App
import com.cflower.doitnow.R
import com.cflower.doitnow.ui.activity.LoginActivity
import com.cflower.doitnow.ui.activity.WorldActivity
import com.cflower.doitnow.viewmodel.MineViewModel
import com.cflower.lib_common.ui.BaseViewModelFragment
import kotlinx.android.synthetic.main.fragment_main_mine.*

class MineFragment : BaseViewModelFragment<MineViewModel>() {
    override val layoutRes: Int = R.layout.fragment_main_mine
    override val viewModelClass: Class<MineViewModel> = MineViewModel::class.java


    override fun View.onCreated(savedInstanceState: Bundle?) {
        setEvent()
    }

    private fun setEvent(){
        cl_world_mine.setOnClickListener {
            startActivity<WorldActivity>(true)
        }
        cl_achievement_mine.setOnClickListener {
            viewModel.showError()
        }
        cl_gather_mine.setOnClickListener {
            viewModel.showError()
         }
        cl_setting_mine.setOnClickListener {
            viewModel.showError()
        }

        btn_sign_out_mine.setOnClickListener {
            App.userModel.logout()
            startActivity<LoginActivity>(true)
        }
    }



}