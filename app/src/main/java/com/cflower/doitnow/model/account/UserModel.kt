package com.cflower.doitnow.model.account

import com.cflower.lib_common.BaseApp
import com.cflower.lib_common.utils.extensions.editor
import com.cflower.lib_common.utils.extensions.sharedPreferences

/**
 * Create By Hosigus at 2020/5/19
 */
class UserModel {
    var user: User
        private set
    val token: String get() = user.token

    private val sp get() = BaseApp.context.sharedPreferences("dit_user")

    init {
        sp.apply {
            user = User(
                token = getString("token", null) ?: ""
            )
        }
    }

    fun bind(user: User) {
        this.user = user
        sp.editor {
            putString("token", user.token)
        }
    }
}