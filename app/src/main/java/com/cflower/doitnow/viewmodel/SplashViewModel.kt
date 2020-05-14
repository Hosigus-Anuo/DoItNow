package com.cflower.doitnow.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import com.cflower.doitnow.net.DownService
import com.cflower.lib_common.BaseApp
import com.cflower.lib_common.network.ApiGenerator
import com.cflower.lib_common.utils.LogManager
import com.cflower.lib_common.utils.extensions.isResponseOK
import com.cflower.lib_common.utils.extensions.safeSubscribeBy
import com.cflower.lib_common.viewmodel.BaseViewModel
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Create By Hosigus at 2020/5/14
 */
class SplashViewModel : BaseViewModel() {
    val SPLASH_FILE_NAME = "splash.png"

    val splashImgFile by lazy(LazyThreadSafetyMode.NONE) {
        File(BaseApp.context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), SPLASH_FILE_NAME)
    }

    fun refreshSplash() {
        val d = ApiGenerator.getApiService(DownService::class.java).downSplash()
            .subscribeOn(Schedulers.io())
            .safeSubscribeBy {
                if (it.isResponseOK && it.data != null && !it.data?.url.isNullOrBlank()) {
                    val url = it.data?.url ?: return@safeSubscribeBy
                    downloadSplash(url)
                    // 本地存储
                } else {
                    // 检查本地过期，删图片
                }
            }
    }

    private fun downloadSplash(mUrl: String) {
        val client = OkHttpClient()
        val request = Request.Builder().url(mUrl).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                LogManager.d("splash_download", "Download failed")
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let {
                    val file = splashImgFile
                    try {
                        if (!file.exists())
                            file.createNewFile()
                        val fos = FileOutputStream(file)

                        BitmapFactory.decodeStream(it.byteStream()).compress(Bitmap.CompressFormat.JPEG, 100, fos)
                        fos.flush()
                        fos.close()

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })

    }
}