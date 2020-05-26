package com.cflower.doitnow.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelStoreOwner
import com.cflower.doitnow.R
import com.cflower.doitnow.viewmodel.LoginViewModel
import com.cflower.lib_common.ui.BaseViewModelFragment
import kotlinx.android.synthetic.main.fragment_register.view.*

class RegisterFragment : BaseViewModelFragment<LoginViewModel>() {

    override val layoutRes: Int = R.layout.fragment_register
    override val viewModelClass: Class<LoginViewModel> = LoginViewModel::class.java
    override val viewModelStoreOwner: ViewModelStoreOwner
        get() = activity ?: this

    override fun View.onCreated(savedInstanceState: Bundle?) {
        edt_name_register.setText(viewModel.userModel.user.userName)
        btn_register.setOnClickListener {
            viewModel.register(edt_name_register.text.toString(), edt_pwd_register.text.toString())
        }
    }

}