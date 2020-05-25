package com.cflower.doitnow.ui.fragment

import android.os.Bundle
import android.view.View
import com.cflower.doitnow.R
import com.cflower.doitnow.ui.activity.MainActivity
import com.cflower.doitnow.viewmodel.LoginViewModel
import com.cflower.lib_common.ui.BaseViewModelFragment
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : BaseViewModelFragment<LoginViewModel>() {

    override val layoutRes: Int = R.layout.fragment_login
    override val viewModelClass: Class<LoginViewModel> = LoginViewModel::class.java

    override fun View.onCreated(savedInstanceState: Bundle?) {
        edt_name_login.setText(viewModel.userModel.user.userName)

        btn_login.setOnClickListener {
            viewModel.login(edt_name_login.text.toString(), edt_pwd_login.text.toString())
        }

        viewModel.loginSuccess.observe {
            if (it == true) {
                activity?.let { activity ->
                    startActivity<MainActivity>()
                    activity.finish()
                }
            }
        }


    }

}