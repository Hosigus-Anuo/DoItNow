package com.cflower.doitnow.ui.widget

import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.cflower.doitnow.R

/**
 * Create By Hosigus at 2020/5/20
 */
class RvFooter @JvmOverloads constructor(
    context: Context,
    textSize: Int = 13
) : LinearLayout(context) {
    private val textView: TextView
    private var state: State = State.LOADING

    init {
        setBackgroundColor(ContextCompat.getColor(context, R.color.secondaryWindowBackground))
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        textView = TextView(context).apply {
            this.textSize = textSize.toFloat()
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            gravity = Gravity.CENTER
            setPadding(0, 40, 0, 40)
        }
        addView(textView)
        showLoading()
    }

    fun showLoading() {
        textView.text = "加载中..."
        state = State.LOADING
    }

    fun showLoadError() {
        textView.text = "加载错误(＞﹏＜)"
        state = State.ERROR
    }

    fun showNoMore() {
        textView.text = "没有更多了~"
        state = State.NOMORE
    }

    fun showSuccess() {
        textView.text = "加载成功(‾◡◝)"
        state = State.SUCCESS
    }

    fun showNothing() {
        textView.text = "什么都没有额（ ’ - ’ * )"
        state = State.NOTHING
    }

    fun setState(s: State) {
        when (s) {
            State.LOADING -> {
                showLoading()
            }
            State.SUCCESS -> {
                showSuccess()
            }
            State.ERROR -> {
                showLoadError()
            }
            State.NOMORE -> {
                showNoMore()
            }
            State.NOTHING -> {
                showNothing()
            }
        }
    }

    enum class State {
        SUCCESS, LOADING, ERROR, NOMORE, NOTHING
    }

}