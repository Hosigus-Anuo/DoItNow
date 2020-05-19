package com.cflower.doitnow.net

import com.cflower.doitnow.model.account.User
import com.cflower.lib_common.model.ModelWrapper
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import io.reactivex.Observable

interface LoginService {
    @POST("")
    @FormUrlEncoded
    fun login(
        @Field("") userName:String,
        @Field(" ")pwd:String
    ): Observable<ModelWrapper<User>>
}