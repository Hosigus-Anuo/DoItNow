package com.cflower.doitnow.ui.activity

import android.content.Intent
import android.os.Bundle
import com.cflower.doitnow.R
import com.cflower.lib_common.BaseApp.Companion.context
import com.cflower.lib_common.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_world.*


class WorldActivity : BaseActivity() {
    override val resId: Int get() = R.layout.activity_world

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(resId)
        setEvent()
    }

    private fun setEvent() {
        img_back_world.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
        rl_change_autograph.setOnClickListener{
            val intent = Intent(context,AutoGraphActivity::class.java)
            startActivity(intent)
        }
        rl_change_password.setOnClickListener {
            val intent = Intent(context,PasswordActivity::class.java)
            startActivity(intent)
        }

    }
}
