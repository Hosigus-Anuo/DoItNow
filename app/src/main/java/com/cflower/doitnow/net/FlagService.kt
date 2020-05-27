package com.cflower.doitnow.net

import com.cflower.doitnow.model.flag.FlagWrapper
import com.cflower.lib_common.model.ModelWrapper
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Create By Hosigus at 2020/5/27
 */
interface FlagService {
    @POST("goal/list")
    @FormUrlEncoded
    fun getFlagList(
        @Field("username")
        owner: String
    ): Observable<ModelWrapper<FlagWrapper>>

    @POST("goal/create")
    @FormUrlEncoded
    fun createFlag(
        @Field("goalname")
        title: String,
        @Field("colortag")
        tag: Int,
        @Field("typetag")
        type: Int,
        @Field("content")
        content: String,
        @Field("ispublic")
        isPublic: Boolean
    ): Observable<ModelWrapper<Any>>
}