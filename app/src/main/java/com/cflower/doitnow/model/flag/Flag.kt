package com.cflower.doitnow.model.flag

import com.cflower.doitnow.R
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Create By Hosigus at 2020/5/25
 */
data class Flag(
    @SerializedName("starttime")
    val startTime: Int = 0,
    @SerializedName("endtime")
    val endTime: Int = 0,
    @SerializedName("colortag")
    private val tagId: Int = 0,
    @SerializedName("goalname")
    val title: String = "",
    @SerializedName("ispublic")
    val isPublic: Boolean = false,
    @SerializedName("pic")
    val pic: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("username")
    val owner: String = "",
    @Expose
    var type: Type = Type.TODAY
) {
    val tag: Tag get() = Tag.values()[tagId]

    enum class Type(val describe: String) {
        TODAY("今日待办"), HABIT("习惯养成"), TARGET("长期目标")
    }

    enum class Tag(val drawable: Int, val describe: String) {
        STUDY(R.drawable.study_tag, "学习"),
        REVIEW(R.drawable.review_tag, "复习"),
        WORK(R.drawable.work_tag, "工作"),
        EXERCISE(R.drawable.excise_tag, "锻炼")
    }
}