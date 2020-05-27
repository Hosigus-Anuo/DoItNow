package com.cflower.doitnow.ui.activity

import android.os.Bundle
import android.view.View
import com.cflower.doitnow.R
import com.cflower.lib_common.ui.BaseActivity

class AutoGraphActivity : BaseActivity() {
    override val resId: Int get() = R.layout.activity_autograph
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        common_toolbar.initWithSplitLine(
            "修改签名",
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
