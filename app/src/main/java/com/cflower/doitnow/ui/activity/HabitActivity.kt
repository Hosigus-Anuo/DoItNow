package com.cflower.doitnow.ui.activity

import android.os.Bundle
import android.view.View
import com.cflower.doitnow.R
import com.cflower.lib_common.ui.BaseActivity

class HabitActivity : BaseActivity() {
    override val resId: Int = R.layout.activity_habit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        common_toolbar.initWithSplitLine(
            "习惯21日",
            false,
            R.drawable.back_white,
            View.OnClickListener {
                startActivity<MainActivity>()
            },
            false
        )
    }
}
