package com.cflower.lib_common.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.cflower.lib_common.R
import com.cflower.lib_common.utils.extensions.dp2px
import com.cflower.lib_common.utils.extensions.getScreenWidth

/**
 * Create By Hosigus at 2020/5/13
 */
class DToolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {
    private var isTitleAtLeft = true
    private var mTitleTextView: TextView? = null
    private var mSubtitleTextView: TextView? = null
    private val paint = Paint()

    var isSplitLineOn = true

    init {
        paint.color = ContextCompat.getColor(context, R.color.commonDefaultDivideLineColor)
        paint.alpha = 25
    }

    override fun setTitle(title: CharSequence?) {
        super.setTitle(title)
        mTitleTextView = getTitleTv("mTitleTextView")
        mTitleTextView?.setTextColor(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.getColor(R.color.levelTwoFontColor)
            } else {
                ContextCompat.getColor(context, R.color.levelTwoFontColor)
            }
        )
    }

    override fun setTitleTextColor(color: Int) {
        super.setTitleTextColor(color)
        mTitleTextView?.setTextColor(color)
    }

    override fun setSubtitle(subtitle: CharSequence?) {
        super.setSubtitle(subtitle)
        mSubtitleTextView = getTitleTv("mSubtitleTextView")
    }

    @SuppressLint("ResourceAsColor")
    private fun getTitleTv(name: String): TextView? {
        try {
            val field = javaClass.superclass?.getDeclaredField(name) ?: return null
            field.isAccessible = true
            val view = field[this] as TextView
            view.paint.isFakeBoldText = true
            view.setTextColor(R.color.levelTwoFontColor)
            return view
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        reLayoutTitle(mTitleTextView)
        reLayoutTitle(mSubtitleTextView)
    }

    //设置title为左对齐或是居中
    fun setTitleLocationAtLeft(isLeft: Boolean) {
        isTitleAtLeft = isLeft
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        //为什么这个screenWidth绘制不满一个屏幕的宽度？？？
        if (isSplitLineOn) canvas.drawLine(
            0f,
            measuredHeight - context.dp2px(1f).toFloat(),
            2 * context.getScreenWidth().toFloat(),
            measuredHeight - context.dp2px(1f).toFloat(),
            paint
        )
    }

    private fun reLayoutTitleToLeft(title: TextView?) {
        if (title == null || !isTitleAtLeft) return
        val ir = getChildAt(0).right
        title.layout(ir, title.top, ir + title.measuredWidth, title.bottom)
    }

    private fun reLayoutTitleToCenter(title: TextView) { //note: o for old ,t for temp, l for left...
        val ol = title.left
        val width = title.measuredWidth
        val tl = (measuredWidth - width) / 2
        if (tl > ol) {
            title.layout(tl, title.top, tl + width, title.bottom)
        }
    }

    private fun reLayoutTitle(title: TextView?) {
        if (title == null) return
        if (isTitleAtLeft) {
            reLayoutTitleToLeft(title)
        } else {
            reLayoutTitleToCenter(title)
        }
    }

}