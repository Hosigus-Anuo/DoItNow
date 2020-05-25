package com.cflower.doitnow.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.cflower.doitnow.R
import com.cflower.doitnow.model.flag.Flag
import com.cflower.doitnow.ui.adapter.FlagRvAdapter
import com.cflower.lib_common.utils.extensions.dp2px
import com.cflower.lib_common.utils.extensions.sp2px
import kotlin.math.max


/**
 * Create By Hosigus at 2020/5/25
 */
class FlagRvDecoration(context: Context, private val adapter: FlagRvAdapter) :
    RecyclerView.ItemDecoration() {

    private val mOffset: Int = context.dp2px(24f)
    private val mTextSize: Float = context.sp2px(16f).toFloat()
    private val mPaint: Paint

    init {
        mPaint = Paint().apply {
            color = ResourcesCompat.getColor(context.resources, R.color.colorText, null)
            isAntiAlias = true
            textSize = mTextSize
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if (position == 0 || adapter.getItemViewType(position) != adapter.getItemViewType(position - 1)) {
            outRect.set(0, mOffset, 0, 0)
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (parent.childCount < 1) {
            return
        }

        val left = parent.left
        val right = parent.right

        var preType = -1

        parent.children.forEach {
            val curType = adapter.getItemViewType(parent.getChildAdapterPosition(it))
            if (preType == curType) return
            preType = curType

            val top = max(mOffset, it.top)
            drawDecoration(left, top - mOffset, right, top, c, curType)
        }
    }

    private val decorationRect = Rect()
    private fun drawDecoration(left: Int, top: Int, right: Int,bottom : Int, c: Canvas, curType: Int) {
        val decoration = Flag.Type.values()[curType].descripe
        mPaint.getTextBounds(decoration, 0, decoration.length, decorationRect)
        val fontMetrics = mPaint.fontMetricsInt
        val x = (left + right - decorationRect.width()) / 2f
        val y = (bottom + top - fontMetrics.bottom - fontMetrics.top) / 2f
        c.drawText(decoration, x, y, mPaint)
    }
}