package com.cflower.doitnow.ui.activity

import android.os.Bundle
import com.cflower.doitnow.R
import com.cflower.lib_common.ui.BaseActivity

class TodayActivity : BaseActivity() {
    override val resId: Int = R.layout.activity_today

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        common_toolbar.initWithSplitLine(getString(R.string.tv_title_today))
    }
}
