package com.cflower.doitnow.model.account

import android.util.Base64
import com.cflower.lib_common.BaseApp
import com.cflower.lib_common.utils.extensions.editor
import com.cflower.lib_common.utils.extensions.getInt
import com.cflower.lib_common.utils.extensions.getString
import com.cflower.lib_common.utils.extensions.sharedPreferences

/**
 * Create By Hosigus at 2020/5/19
 */
class UserModel {
    var user: User
        private set
    var token: Token
        get() {
            return if (field.duration < 1) {
                tempToken
            } else {
                field
            }
        }
        private set

    private var tempToken: Token = Token()

    val isLogin: Boolean get() = token.duration > 0

    private val sp get() = BaseApp.context.sharedPreferences("dit_user")

    init {
        sp.apply {
            token = Token(
                duration = getInt("token_duration"),
                token = getString("token")
            )
            user = User(
                userName = getString("user_name"),
                autograph = getString("autograph"),
                avatar = getString("avatar"),
                achievement = Achievement(
                    habit = getInt("habit"),
                    task = getInt("task"),
                    persist = getInt("persist"),
                    time = getInt("time"),
                    target = getInt("target")
                )
            )
        }
    }

    fun login(name: String, pwd: String) {
        user = user.copy(userName = name)
        tempToken = Token(0, Base64.encodeToString("$name:$pwd".toByteArray(), Base64.DEFAULT))
    }

    fun updateToken(token: Token) {
        this.token = token
        sp.editor {
            putString("token", token.token)
            putInt("token_duration", token.duration)
        }
    }

    fun bind(user: User) {
        this.user = user
        sp.editor {
            putString("user_name", user.userName)
            putString("autograph", user.autograph)
            putString("avatar", user.avatar)
            putInt("habit", user.achievement.habit)
            putInt("task", user.achievement.task)
            putInt("persist", user.achievement.persist)
            putInt("time", user.achievement.time)
            putInt("target", user.achievement.target)
        }
    }
}