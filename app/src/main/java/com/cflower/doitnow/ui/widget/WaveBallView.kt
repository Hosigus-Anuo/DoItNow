package com.cflower.doitnow.ui.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import com.cflower.lib_common.utils.extensions.dp2px
import com.cflower.lib_common.utils.extensions.forEachObject
import kotlin.math.PI
import kotlin.math.min
import kotlin.math.sin

class WaveBallView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private lateinit var mBallBitmap: Bitmap //球形遮罩
    private lateinit var edgeRectF: RectF

    private val speedUpdateTime = 1500L
    private val porterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
    private val mWavePaint: Paint = Paint() //球形遮罩
    private val edgePaint = Paint()
    private val waves = listOf(Wave(), Wave())

    private val startWaveAnimator: ValueAnimator// 平稳转波动动画
    private val stopWaveAnimator: ValueAnimator// 波动转平稳动画
    private val normalWaveAnimator: ValueAnimator// 重复波动动画

    private var isWaving: Boolean = false
    var mProgress: Float = 0f //当前的进度 0-1
        private set
    var mFullLength: Long = 5000L
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

        val updateListener = ValueAnimator.AnimatorUpdateListener {
            val p = it.animatedValue as Float
            waves.forEachObject {
                offset += speedMAX * p
                height = heightMAX * p
            }
            postInvalidate()
        }

        stopWaveAnimator = ValueAnimator.ofFloat(1f, 0f)
            .setDuration(speedUpdateTime)
            .apply {
                interpolator = AccelerateInterpolator()
                doOnEnd {
                    isWaving = false
                }
                addUpdateListener(updateListener)
            }

        normalWaveAnimator = ValueAnimator.ofFloat(0f, 1f)
            .apply {
                interpolator = LinearInterpolator()
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        stopWaveAnimator.start()
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                        stopWaveAnimator.start()
                    }
                })
                addUpdateListener {
                    mProgress = it.animatedFraction
                    waves.forEachObject {
                        offset += speedMAX
                    }
                    postInvalidate()
                }
            }

        startWaveAnimator = ValueAnimator.ofFloat(0f, 1f)
            .setDuration(speedUpdateTime)
            .apply {
                interpolator = AccelerateInterpolator()
                doOnEnd {
                    normalWaveAnimator.start()
                }
                doOnCancel {
                    stopWaveAnimator.start()
                }
                addUpdateListener(updateListener)
            }

    }

    fun start(fullLength: Long = mFullLength, progress: Float = mProgress) {
        isWaving = true

        if (normalWaveAnimator.isRunning) {
            normalWaveAnimator.cancel()
        }

        normalWaveAnimator.apply {
            duration = fullLength
            currentPlayTime = (progress * fullLength).toLong()
        }

        startWaveAnimator.start()
    }

    fun pause() {
        startWaveAnimator.cancel()
        normalWaveAnimator.cancel()
    }

    fun stop() {
        pause()
        mProgress = 0f
        postInvalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0 && h > 0) {
            edgeRectF = RectF(0F, 0F, w.toFloat(), h.toFloat())
            waves[0].apply {
                speedMAX = w / 70f
                heightMAX = min(h / 6f, context.dp2px(15F).toFloat())
                cycle = (3 * PI / w).toFloat()
            }

            waves[1].apply {
                speedMAX = w / 140f
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

        if (isWaving) {
            repeat(width) {
                waves.forEach { w ->
                    val waveY = getWaveY(it, w)
                    canvas.drawLine(it.toFloat(), waveY, it.toFloat(), height.toFloat(), mWavePaint)
                }
            }
        } else {
            val mHeight: Float = (1 - mProgress) * height
            waves.forEach {
                canvas.drawRect(0F, mHeight, width.toFloat(), height.toFloat(), mWavePaint)
            }
        }
        mWavePaint.xfermode = porterDuffXfermode
        canvas.drawBitmap(mBallBitmap, 0F, 0F, mWavePaint)
        mWavePaint.xfermode = null
        canvas.restoreToCount(sc)
    }

    private fun getWaveY(x: Int, w: Wave): Float {
        return w.height * sin(w.cycle * (x + w.offset)) + (1 - mProgress) * height
    }

    private class Wave(
        var cycle: Float = 0f,//波浪的周期
        var offset: Float = 0f, //波浪的偏移
        var height: Float = 0f,//波浪的当前高度

        var heightMAX: Float = 0f,//波浪的振幅
        var speedMAX: Float = 0f//波浪的MAX速度
    )
}