package com.cflower.doitnow.ui.activity

import android.os.Bundle
import android.view.View
import com.cflower.doitnow.R
import com.cflower.lib_common.ui.BaseActivity

class TodayActivity : BaseActivity() {
    override val resId: Int = R.layout.activity_today

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        common_toolbar.initWithSplitLine(getString(R.string.tv_title_today),false,R.drawable.back_white,
            View.OnClickListener {
                startActivity<MainActivity>()
            },false)
    }
}
