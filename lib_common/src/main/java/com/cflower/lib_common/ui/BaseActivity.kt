package com.cflower.lib_common.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.cflower.lib_common.BaseApp
import com.cflower.lib_common.R
import com.cflower.lib_common.ui.widget.DToolbar
import com.cflower.lib_common.utils.extensions.fillIntentArguments
import kotlinx.android.synthetic.main.common_toolbar.*

/**
 * Create By Hosigus at 2020/5/13
 */
abstract class BaseActivity : AppCompatActivity() {

    // 日志
    protected open var TAG: String = this.javaClass.simpleName

    // 布局id
    @get:LayoutRes
    abstract val resId: Int

    val common_toolbar get() = toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWindows()
        setContentView(resId)
    }

    private fun initWindows() {
        when {
            Build.VERSION.SDK_INT >= 23 -> {
                if (BaseApp.isNightMode) {
                    window.decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                } else {
                    window.decorView.systemUiVisibility =
                            //亮色模式状态栏
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
                                //设置decorView的布局设置为全屏
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                                //维持布局稳定，不会因为statusBar和虚拟按键的消失而移动view位置
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                }
            }
            Build.VERSION.SDK_INT >= 21 -> {
                //设置decorView的布局设置为全屏，并维持布局稳定
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                window.statusBarColor = Color.TRANSPARENT
            }
        }
    }

    inline fun <reified T : Activity> startActivity(vararg params: Pair<String, Any?>) {
        startActivity(
            Intent(this@BaseActivity, T::class.java)
                .fillIntentArguments(params)
        )
    }

    inline fun <reified T : Activity> startActivityFinish(
        vararg params: Pair<String, Any?>
    ) {
        startActivity<T>(*params)
        finish()
    }

    protected fun DToolbar.initWithSplitLine(
        title: String,
        withSplitLine: Boolean = true,
        @DrawableRes icon: Int = R.drawable.common_ic_back,
        listener: View.OnClickListener? = View.OnClickListener { finish() },
        titleOnLeft: Boolean = true
    ) {
        isSplitLineOn = withSplitLine
        this.title = title

        setSupportActionBar(this)
        setTitleLocationAtLeft(titleOnLeft)
        if (listener == null) {
            navigationIcon = null
        } else {
            setNavigationIcon(icon)
            setNavigationOnClickListener(listener)
        }
    }

}