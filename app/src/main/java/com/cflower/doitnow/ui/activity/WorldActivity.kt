package com.cflower.doitnow.ui.activity

import android.os.Bundle
import android.view.View
import com.cflower.doitnow.R
import com.cflower.lib_common.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_world.*

class WorldActivity : BaseActivity() {
    override val resId: Int get() = R.layout.activity_world

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(resId)
        common_toolbar.initWithSplitLine("我的世界",false,R.drawable.back_white, View.OnClickListener {
            startActivity<MainActivity>()
        },false)
        setEvent()
    }

    private fun setEvent() {
        rl_change_autograph.setOnClickListener {
            startActivity<AutoGraphActivity>()
        }
        rl_change_password.setOnClickListener {
            startActivity<PasswordActivity>()
        }
        rl_change_avatar.setOnClickListener {

        }

    }


}
