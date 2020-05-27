package com.cflower.doitnow.ui.activity

import android.os.Bundle
import com.cflower.doitnow.R
import com.cflower.doitnow.viewmodel.FlagViewModel
import com.cflower.lib_common.ui.BaseViewModelActivity

class TodayActivity : BaseViewModelActivity<FlagViewModel>() {
    override val resId: Int = R.layout.activity_today
    override val viewModelClass: Class<FlagViewModel> = FlagViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        common_toolbar.initWithSplitLine(
            getString(R.string.tv_title_today),
            false,
            R.drawable.back_white,
            titleOnLeft = false
        )

        viewModel.postFlagStatus.observe {
            if (it == true) {
                finish()
            }
        }
    }

}
