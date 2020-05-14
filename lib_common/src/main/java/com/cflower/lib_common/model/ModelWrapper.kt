package com.cflower.lib_common.model

import com.google.gson.annotations.SerializedName

data class ModelWrapper<T>(
    @SerializedName("data")
    val data: T? = null,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Int = 0
)