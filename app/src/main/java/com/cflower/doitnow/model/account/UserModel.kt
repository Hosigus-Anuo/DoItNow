package com.cflower.doitnow.model.account

import com.cflower.lib_common.BaseApp
import com.cflower.lib_common.utils.extensions.sharedPreferences

/**
 * Create By Hosigus at 2020/5/19
 */
class UserModel {
    var user: User
    val token: String get() = user.token

    init {
        BaseApp.context.sharedPreferences("dit_user").apply {
            user = User(
                token = getString("token", null) ?: ""
            )
        }
    }
}