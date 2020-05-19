package com.cflower.doitnow.ui.activity

import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.cflower.doitnow.R
import com.cflower.doitnow.viewmodel.SplashViewModel
import com.cflower.lib_common.ui.BaseViewModelActivity
import kotlinx.android.synthetic.main.activity_splash.*
import java.io.File

class SplashActivity : BaseViewModelActivity<SplashViewModel>() {

    override val viewModelClass: Class<SplashViewModel> = SplashViewModel::class.java
    override val resId: Int = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadSplash()

        // todo 多入口分流

        object : CountDownTimer(3000, 1000) {
            override fun onFinish() {
                if (!isFinishing)
                    finishSplash<LoginActivity>()
            }
            override fun onTick(millisUntilFinished: Long) {
                runOnUiThread {
                    val str = "跳过 ${millisUntilFinished / 1000}"
                    btn_splash_skip.text = str
                }
            }
        }.start()
        btn_splash_skip.setOnClickListener {
            finishSplash<MainActivity>()
        }
    }

    private fun loadSplash() {
        val splashImgFile = viewModel.splashImgFile
        if (splashImgFile.exists()) {
            Glide.with(this)
                .load(splashImgFile)
                .apply(RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(splash_view)
        }
        viewModel.refreshSplash()
    }

    private inline fun <reified T : Activity>  finishSplash() {
        startActivity<T>()
        finish()
    }

}
