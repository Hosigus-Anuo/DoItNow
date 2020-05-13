package com.cflower.lib_common.utils.extensions

import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import com.cflower.lib_common.BaseApp
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.io.File

/**
 * Created By jay68 on 2018/8/15.
 */
val File.uri: Uri
    get() = if (Build.VERSION.SDK_INT >= 24) {
        FileProvider.getUriForFile(BaseApp.context, "com.mredrock.cyxbs.fileProvider", this)
    } else {
        Uri.fromFile(this)
    }

fun File.getRequestBody(): RequestBody {
    return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), this)
}