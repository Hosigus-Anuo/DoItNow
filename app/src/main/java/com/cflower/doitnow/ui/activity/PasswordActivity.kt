package com.cflower.doitnow.ui.activity

import android.content.Intent
import android.os.Bundle
import com.cflower.doitnow.R
import com.cflower.lib_common.BaseApp.Companion.context
import com.cflower.lib_common.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_password.*

class PasswordActivity : BaseActivity() {
    override val resId: Int get() = R.layout.activity_password
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(resId)
        setEvent()
    }

    private fun setEvent() {
        img_back_password.setOnClickListener {
            val intent = Intent(context, WorldActivity::class.java)
            startActivity(intent)
        }
    }
}
