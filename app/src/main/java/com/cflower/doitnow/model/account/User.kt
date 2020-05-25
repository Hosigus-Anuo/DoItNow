package com.cflower.doitnow.model.account

import com.google.gson.annotations.SerializedName

/**
 * Create By Hosigus at 2020/5/14
 */
data class User(
    @SerializedName("username")
    val userName: String = "",
    val token: String = "",
    @SerializedName("selfdes")
    val autograph: String = "",
    val achievement: Achievement,
    @SerializedName("avatar")
    val avatar: String = ""
)