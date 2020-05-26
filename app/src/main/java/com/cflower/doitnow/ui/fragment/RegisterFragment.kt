package com.cflower.doitnow.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cflower.doitnow.R
import com.cflower.doitnow.ui.activity.MainActivity
import com.cflower.doitnow.viewmodel.LoginViewModel
import com.cflower.doitnow.viewmodel.RegisterViewModel
import com.cflower.lib_common.ui.BaseViewModelFragment
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_register.view.*

class RegisterFragment: BaseViewModelFragment<RegisterViewModel>()  {

    override val layoutRes: Int = R.layout.fragment_register
    override val viewModelClass: Class<RegisterViewModel> = RegisterViewModel::class.java

    override fun View.onCreated(savedInstanceState: Bundle?) {
        edt_name_register.setText(viewModel.userModel.user.userName)

        btn_login.setOnClickListener {
            viewModel.register(edt_name_register.text.toString(), edt_pwd_register.text.toString())
        }

        viewModel.registerSuccess.observe {
            if (it == true) {
                activity?.let { activity ->
                    startActivity<MainActivity>()
                    activity.finish()
                }
            }
        }
    }

}