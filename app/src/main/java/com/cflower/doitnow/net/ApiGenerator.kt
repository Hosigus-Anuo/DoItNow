package com.cflower.doitnow.net

import com.cflower.doitnow.App
import com.cflower.lib_common.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Create By Hosigus at 2020/5/13
 */

object ApiGenerator {
    private const val DEFAULT_TIME_OUT = 30
    private const val BASE_URL = "http://118.89.182.225/api/v1/"

    private var retrofit: Retrofit
    private var okHttpClient: OkHttpClient

    init {
        okHttpClient = configureOkHttp(OkHttpClient.Builder())
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun configureOkHttp(builder: OkHttpClient.Builder): OkHttpClient {
        return builder.apply {
            connectTimeout(DEFAULT_TIME_OUT.toLong(), TimeUnit.SECONDS)
            interceptors().add(Interceptor {
                it.proceed(
                    it.request().newBuilder().header(
                        "Authorization",
                        "Basic ${App.userModel.token.token}"
                    ).build()
                )
            })
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(logging)
            }
        }.build()
    }


    fun <T> getApiService(clazz: Class<T>) = retrofit.create(clazz)

}