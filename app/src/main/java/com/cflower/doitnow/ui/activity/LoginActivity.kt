package com.cflower.doitnow.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.cflower.doitnow.R
import com.cflower.doitnow.ui.adapter.SimpleFragmentAdapter
import com.cflower.doitnow.ui.fragment.LoginFragment
import com.cflower.doitnow.ui.fragment.RegisterFragment
import com.cflower.doitnow.viewmodel.LoginViewModel
import com.cflower.lib_common.ui.BaseViewModelActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseViewModelActivity<LoginViewModel>() {
    override val resId: Int = R.layout.activity_login
    override val viewModelClass: Class<LoginViewModel> = LoginViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loginSuccess.observe {
            if (it == true) {
                startActivity<MainActivity>()
                finish()
            }
        }

        initTabLayout()
    }

    private fun initTabLayout() {
        vp_account.apply {
            offscreenPageLimit = 2
            adapter = SimpleFragmentAdapter(
                listOf("登录", "注册"),
                listOf<Fragment>(LoginFragment(), RegisterFragment()),
                supportFragmentManager
            )
            currentItem = 0
        }

        tv_account.setupWithViewPager(vp_account)
    }

}
