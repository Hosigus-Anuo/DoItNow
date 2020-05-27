package com.cflower.doitnow.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.cflower.doitnow.R
import com.cflower.doitnow.ui.widget.SingleChoiceDialogFragment
import com.cflower.doitnow.viewmodel.TargetViewModel
import com.cflower.lib_common.ui.BaseViewModelFragment
import kotlinx.android.synthetic.main.fragment_main_target.*


class TargetFragment : BaseViewModelFragment<TargetViewModel>() {
    override val layoutRes: Int = R.layout.fragment_main_target
    override val viewModelClass: Class<TargetViewModel> = TargetViewModel::class.java
    var time:Int = 25
    override fun View.onCreated(savedInstanceState: Bundle?) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_operate_clock.setOnClickListener {
            if (btn_operate_clock.text == "开始") {
                btn_operate_clock.text = "暂停"
                wbv_time_clock.start(10000L)
                val mRunnable = Runnable {
                    run {

                        println("5s后打印输出")
                    }
                }
            } else {
                btn_operate_clock.text = "开始"
                wbv_time_clock.pause()
            }
        }

        btn_stop_clock.setOnClickListener {
            wbv_time_clock.stop()
        }

        tv_time_clock.setOnClickListener {
            showSingleChoiceDialogFragment()
        }

    }


    private fun showSingleChoiceDialogFragment() {
        var index: Int = 0
        val singleChoiceDialogFragment = SingleChoiceDialogFragment()
        val items = arrayOf("25:00", "50:00", "75:00")
        singleChoiceDialogFragment.show(
            "选择学习时间", items,
            DialogInterface.OnClickListener { _, which -> index = which },
            DialogInterface.OnClickListener { _, _ ->
                tv_time_clock.text = items[index]
                time = items[index].split(":")[0].toInt()
            }, fragmentManager
        )
    }
}