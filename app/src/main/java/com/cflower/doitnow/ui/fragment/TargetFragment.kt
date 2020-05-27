package com.cflower.doitnow.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.cflower.doitnow.R
import com.cflower.doitnow.ui.widget.SingleChoiceDialogFragment
import com.cflower.doitnow.viewmodel.TargetViewModel
import com.cflower.lib_common.ui.BaseViewModelFragment
import kotlinx.android.synthetic.main.fragment_main_target.*


class TargetFragment : BaseViewModelFragment<TargetViewModel>() {
    override val layoutRes: Int = R.layout.fragment_main_target
    override val viewModelClass: Class<TargetViewModel> = TargetViewModel::class.java
    var time: Int = 25
    lateinit var countDownTimer: CountDownTimer
    override fun View.onCreated(savedInstanceState: Bundle?) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_operate_clock.setOnClickListener {
            if (btn_operate_clock.text == "开始") {
                btn_operate_clock.text = "暂停"
                wbv_time_clock.start(1000L * time * 60)

                start(tv_time_clock.text.toString())
            } else {
                btn_operate_clock.text = "开始"
                wbv_time_clock.pause()
                pause()
            }
        }

        btn_stop_clock.setOnClickListener {
            wbv_time_clock.stop()
            stop()
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

    private fun start(time: String) {
        val branch: Int = time.split(":")[0].toInt()
        val second: Int = time.split(":")[1].toInt()
        val t: Long = 1000L * (branch * 60 + second)
        countDownTimer = object : CountDownTimer(t, 300) {
            override fun onFinish() {
                tv_time_clock.text = "00:00"
            }

            override fun onTick(millisUntilFinished: Long) {
                val minute = millisUntilFinished / 1000 / 60 % 60
                val seconds = millisUntilFinished / 1000 % 60
                tv_time_clock.text = "$minute:$seconds"
            }
        }.start()
    }

    private fun pause() {
        countDownTimer.cancel()
    }

    private fun stop() {
        countDownTimer.onFinish()
    }

}