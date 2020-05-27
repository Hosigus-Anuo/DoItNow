package com.cflower.doitnow.ui.activity

import android.os.Bundle
import com.cflower.doitnow.R
import com.cflower.doitnow.model.flag.Flag
import com.cflower.doitnow.viewmodel.FlagViewModel
import com.cflower.lib_common.ui.BaseViewModelActivity
import kotlinx.android.synthetic.main.activity_habit.*

class HabitActivity : BaseViewModelActivity<FlagViewModel>() {
    override val resId: Int = R.layout.activity_habit
    override val viewModelClass: Class<FlagViewModel> = FlagViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        common_toolbar.initWithSplitLine("习惯21日", false, titleOnLeft = false)
        viewModel.postFlagStatus.observe {
            if (it == true) {
                finish()
            }
        }


        btn_accomplish_habit.setOnClickListener {
            viewModel.postFlag(
                Flag(
                    title = edt_name_habit.text.toString(),
                    tagId = 0,
                    type = Flag.Type.HABIT,
                    content = edt_content_habit.text.toString(),
                    isPublic = s_publish_habit.isChecked
                )
            )
        }
    }

}
