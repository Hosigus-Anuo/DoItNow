package com.cflower.lib_common.utils

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.graphics.Point
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import com.cflower.lib_common.R
import io.reactivex.android.schedulers.AndroidSchedulers

object DToast {
    /** @hide */
    @IntDef(Toast.LENGTH_SHORT, Toast.LENGTH_LONG)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Duration

    @JvmStatic
    @Throws(NotFoundException::class)
    fun makeText(
        context: Context,
        @StringRes resId: Int,
        @Duration duration: Int = Toast.LENGTH_SHORT
    ): Toast {
        return makeText(
            context,
            context.resources.getText(resId),
            duration
        )
    }

    @JvmStatic
    fun makeText(
        context: Context,
        text: CharSequence,
        @Duration duration: Int = Toast.LENGTH_SHORT
    ): Toast {
        val result = Toast(context)
        val inflate =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = inflate.inflate(R.layout.common_din_toast, null)
        val tv = v.findViewById<TextView>(R.id.tv_din_toast)
        tv.text = text
        val wm =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val point = Point()
        display.getSize(point)
        result.view = v
        result.duration = duration
        result.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP, 0, point.y / 8)
        return result
    }

    @JvmStatic
    fun asyncShow(
        context: Context, text: String, @Duration duration: Int = Toast.LENGTH_SHORT
    ) {
        AndroidSchedulers.mainThread().scheduleDirect { makeText(context, text, duration) }
    }

}
