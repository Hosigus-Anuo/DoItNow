package com.cflower.doitnow.net

import com.cflower.doitnow.model.account.User
import com.cflower.lib_common.model.ModelWrapper
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegisterService {
    @POST("/auth/register")
    @FormUrlEncoded
    fun register(
        @Field("username") userName:String,
        @Field("password")pwd:String
    ): Observable<ModelWrapper<User>>
}