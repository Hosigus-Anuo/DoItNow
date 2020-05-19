package com.cflower.doitnow.model.account

import com.google.gson.annotations.SerializedName

/**
 * Create By Hosigus at 2020/5/14
 */
data class User(
    @SerializedName("user_name")
    val userName: String = "",
    val sex: String = "",
    val token: String = ""
)