package com.cflower.doitnow.ui.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.cflower.lib_common.utils.extensions.dp2px
import com.cflower.lib_common.utils.extensions.forEachObject
import kotlin.math.*

class WaveBallView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var mProgressAnimator: ValueAnimator = ObjectAnimator() // 进度增长动画
    private lateinit var mBallBitmap: Bitmap //球形遮罩
    private lateinit var edgeRectF: RectF

    private val porterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
    private val mWavePaint: Paint = Paint() //球形遮罩
    private val edgePaint = Paint()
    private val waves = listOf(Wave(), Wave())

    var mProgress: Int = 0 //当前的进度
        private set

    init {
        mWavePaint.apply {
            color = Color.argb(168, 255, 182, 193)
            isFilterBitmap = true
        }
        edgePaint.apply {
            strokeWidth = 1f
            color = Color.argb(255, 255, 182, 193)
            style = Paint.Style.STROKE
        }
    }

    fun startProgress(progress: Int) {
        startProgress(progress, 1500, 0)
    }

    //设置进度，并且以动画的形式上涨到该进度
    //progress:进度
    //duration:持续时间
    //delay:延时

    fun startProgress(progress: Int, duration: Long, delay: Long) {
        if (mProgressAnimator.isRunning) {
            mProgressAnimator.cancel()
        }
        waves.forEachObject {
            speed = speedMAX * max(abs(mProgress - progress), 50) / 100f
        }
        mProgressAnimator = ValueAnimator.ofInt(mProgress, progress)
            .setDuration(duration)
            .apply {
                interpolator = AccelerateDecelerateInterpolator()
                startDelay = delay
                addUpdateListener {
                    mProgress = it.animatedValue as Int
                    val p = 1 - abs(1 - 2 * it.animatedFraction)
                    waves.forEachObject {
                        offset += speed * p
                        height = heightMAX * p
                    }
                    postInvalidate()
                }
                start()
            }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0 && h > 0) {
            edgeRectF = RectF(0F, 0F, w.toFloat(), h.toFloat())
            waves[0].apply {
                speedMAX = w / 10f
                heightMAX = min(h / 6f, context.dp2px(15F).toFloat())
                cycle = (3 * PI / w).toFloat()
            }

            waves[1].apply {
                speedMAX = w / 17f
                heightMAX = min(h / 15f, context.dp2px(6f).toFloat())
                cycle = (4 * PI / w).toFloat()
            }

            val mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)

            val canvas = Canvas(mBitmap)
            val ball = RectF(0F, 0F, w.toFloat(), h.toFloat())
            canvas.drawOval(ball, mWavePaint)
            mBallBitmap = mBitmap
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (height <= 0 || width <= 0 || canvas == null
            || !this::edgeRectF.isInitialized || !this::mBallBitmap.isInitialized
        ) {
            return
        }

        canvas.drawColor(Color.TRANSPARENT)
        canvas.drawArc(edgeRectF, 0F, 360F, false, edgePaint)

        val sc: Int = canvas.saveLayer(0F, 0F, width.toFloat(), height.toFloat(), null)

        if (mProgressAnimator.isRunning) {
            repeat(width) {
                waves.forEach { w ->
                    val waveY = getWaveY(it, w).toFloat()
                    canvas.drawLine(it.toFloat(), waveY, it.toFloat(), height.toFloat(), mWavePaint)
                }
            }
        } else {
            val mHeight: Float = (1 - mProgress / 100.0F) * height
            waves.forEach {
                canvas.drawRect(0F, mHeight, width.toFloat(), height.toFloat(), mWavePaint)
            }
        }
        mWavePaint.xfermode = porterDuffXfermode
        canvas.drawBitmap(mBallBitmap, 0F, 0F, mWavePaint)
        mWavePaint.xfermode = null
        canvas.restoreToCount(sc)
    }

    private fun getWaveY(x: Int, w: Wave): Double {
        return w.height * sin(w.cycle * (x + w.offset)) + (1 - mProgress / 100.0) * height
    }

    private class Wave(
        var cycle: Float = 0f,//波浪的周期
        var offset: Float = 0f, //波浪的偏移
        var height: Float = 0f,//波浪的当前高度
        var speed: Float = 0f,//波浪的当前速度

        var heightMAX: Float = 0f,//波浪的振幅
        var speedMAX: Float = 0f//波浪的MAX速度
    )
}