package com.cflower.doitnow.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cflower.lib_common.utils.extensions.dp2px
import com.cflower.lib_common.utils.extensions.sp2px
import java.util.*

class FlagItemDecoration(context: Context, adapter: FlagAdapter) : RecyclerView.ItemDecoration() {
    private var mContext: Context = context
    private var mDataList = adapter.getDataList()

    private var mOffset: Int
    private var mTextSize: Int

    private var mPaint: Paint = Paint()

    init {
        mDataList = adapter.getDataList()
        mOffset = mContext.dp2px(24F)
        mTextSize = mContext.sp2px(16F)
        mPaint.apply {
            color = Color.RED
            isAntiAlias = true
            textSize = mTextSize.toFloat()
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position: Int = parent.getChildAdapterPosition(view)
        if (isFirstInGroup(position)) {
            outRect.set(0, mOffset, 0, 0)
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val itemCount: Int = state.itemCount
        val childCount = parent.childCount
        val left: Int = parent.left
        val right: Int = parent.right
        val preTag: String? = null
        var curTag: String
        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)
            val position: Int = parent.getChildAdapterPosition(child)
            curTag = getTag(position)
            if (Objects.equals(curTag, preTag))
                continue
            val bottom: Int = child.bottom
            var top = Math.max(mOffset, child.top)
            if (position + 1 < itemCount) {
                val nextTag: String = getTag(position + 1)
                if (curTag != nextTag && bottom < top) {
                    top = bottom
                }
            }
            drawTag(left, top - mOffset, right, top, c, curTag)
        }
    }

    private fun drawTag(left: Int, top: Int, right: Int, bottom: Int, canvas: Canvas, tag: String) {
        mPaint.color = Color.BLACK
        canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)

        mPaint.color = Color.WHITE
        val rect: Rect = Rect()
        mPaint.getTextBounds(tag, 0, tag.length, rect)
        val fontMetrics: Paint.FontMetricsInt = mPaint.fontMetricsInt
        val x: Int = (left + right - rect.width()) / 2
        val y: Int = (bottom + top - fontMetrics.bottom - fontMetrics.top) / 2
        canvas.drawText(tag, x.toFloat(), y.toFloat(), mPaint)
    }

    private fun isFirstInGroup(position: Int): Boolean {
        if (position != 0) {
            val cur: Int = mDataList.get(position).toInt()
            val pre: Int = mDataList.get(position - 1).toInt()
            return cur / 10 != pre / 10
        }
        return true
    }

    private fun getTag(position: Int): String {
        val num: String = mDataList.get(position)
        return (num.toInt() / 10 * 10).toString() + "+"
    }

}