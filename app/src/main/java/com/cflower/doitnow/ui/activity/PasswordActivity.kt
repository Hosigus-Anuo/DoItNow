package com.cflower.doitnow.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.cflower.doitnow.R
import com.cflower.lib_common.BaseApp.Companion.context
import com.cflower.lib_common.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_password.*

class PasswordActivity : BaseActivity() {
    override val resId: Int get() = R.layout.activity_password
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        common_toolbar.initWithSplitLine(
            "修改密码",
            false,
            R.drawable.back_white,
            View.OnClickListener {
                startActivity<WorldActivity>()
            },
            false
        )
        setEvent()
    }

    private fun setEvent() {

    }
}
