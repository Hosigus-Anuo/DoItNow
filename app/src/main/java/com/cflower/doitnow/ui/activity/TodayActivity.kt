package com.cflower.doitnow.ui.activity

import android.os.Bundle
import com.cflower.doitnow.R
import com.cflower.doitnow.model.flag.Flag
import com.cflower.doitnow.viewmodel.FlagViewModel
import com.cflower.lib_common.ui.BaseViewModelActivity
import kotlinx.android.synthetic.main.activity_today.*

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

        btn_accomplish_today.setOnClickListener {
            viewModel.postFlag(
                Flag(
                    title = edt_name_today.text.toString(),
                    tagId = 0,
                    type = Flag.Type.TODAY,
                    content = et_content_today.text.toString(),
                    isPublic = s_publish_today.isChecked
                )
            )
        }
    }

}
