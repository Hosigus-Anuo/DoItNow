package com.cflower.doitnow.ui.activity

import android.os.Bundle
import com.cflower.doitnow.R
import com.cflower.doitnow.ui.adapter.LoginFragmentAdapter
import com.cflower.lib_common.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    var loginAdapter = LoginFragmentAdapter(supportFragmentManager)
    override val resId: Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        common_toolbar.initWithSplitLine("DO IT NOW",false)
        initTabLayout()
    }

    private fun initTabLayout() {
        vp_account.apply {
            offscreenPageLimit = 2
            adapter = loginAdapter
            currentItem = 0
        }

        tv_account.setupWithViewPager(vp_account)
    }
}
