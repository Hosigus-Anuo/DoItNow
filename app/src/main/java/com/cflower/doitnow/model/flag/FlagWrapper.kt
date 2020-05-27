package com.cflower.doitnow.model.flag

import com.google.gson.annotations.SerializedName

/**
 * Create By Hosigus at 2020/5/27
 */
data class FlagWrapper(
    @SerializedName("0")
    val today: List<Flag> = listOf(),
    @SerializedName("1")
    val habit: List<Flag> = listOf(),
    @SerializedName("2")
    val target: List<Flag> = listOf()
)