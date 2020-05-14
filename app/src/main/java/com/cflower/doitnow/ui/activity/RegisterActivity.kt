package com.cflower.doitnow.ui.activity

import android.os.Bundle
import com.cflower.doitnow.R
import com.cflower.doitnow.viewmodel.RegisterViewModel
import com.cflower.lib_common.ui.BaseViewModelActivity

class RegisterActivity : BaseViewModelActivity<RegisterViewModel>() {
    override val resId: Int = R.layout.activity_register
    override val viewModelClass: Class<RegisterViewModel> = RegisterViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

}
