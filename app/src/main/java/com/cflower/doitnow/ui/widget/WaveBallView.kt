package com.cflower.doitnow.ui.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.cflower.lib_common.utils.extensions.dp2px
import kotlin.math.sin

class WaveBallView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val HANDLER_WHAT_UPDATE: Int = 0x100
    private var mWaveSpeedA: Int = 0 //波浪A的速度
    private var mWaveSpeedB: Int = 0//波浪B的速度
    private var mWaveHeightA: Int = 0//波浪A的振幅
    private var mWaveHeightB: Int = 0//波浪B的振幅
    private var mWaveCycleA: Float = 0f//波浪A的周期
    private var mWaveCycleB: Float = 0f//波浪B的周期
    private var mOffestA: Int = 0   //波浪A的偏移
    private var mOffestB: Int = 0 //波浪B的偏移
    private var mProgress: Int = 0 //当前的进度
    private var isWaving: Boolean = true;    //是否处于波浪状态
    private var mWavePaint: Paint = Paint(); //球形遮罩
    private var mBallBitmap: Bitmap? = null //球形遮罩
    private var mProgressAnimator: ObjectAnimator = ObjectAnimator() // 进度增长动画
    private var mWaveStopAnimator: ObjectAnimator = ObjectAnimator() //波浪停止动画


    private fun init() {
        mWavePaint = Paint()
        mWavePaint.color = Color.argb(60, 255, 182, 193)
        mWavePaint.isFilterBitmap = true
    }

    //获取进度
    fun getProgress(): Int {
        return mProgress
    }

    //设置进度
    private fun setProgress(progress: Int) {
        mProgress = progress
    }

    fun getWaveHeightA(): Int {
        return mWaveHeightA
    }

    fun setWaveHeightA(waveHeightA: Int) {
        mWaveHeightA = waveHeightA
    }

    fun getWaveHeightB(): Int {
        return mWaveHeightB
    }

    fun setWaveHeightB(waveHeightB: Int) {
        mWaveHeightB = waveHeightB
    }

    fun startProgress(progress: Int) {
        startProgress(progress, 1000, 0)
    }

    //设置进度，并且以动画的形式上涨到该进度
    //progress:进度
    //duration:持续时间
    //delay:延时

    private fun startProgress(progress: Int, duration: Long, delay: Long) {
        if (mProgressAnimator.isRunning) {
            mProgressAnimator.cancel()
        }
        if (mWaveStopAnimator.isRunning) {
            mWaveStopAnimator.cancel()
        }
        isWaving = true
        mProgressAnimator = ObjectAnimator.ofInt(this, "Progress", progress)
        mProgressAnimator.duration = duration
        mProgressAnimator.startDelay = delay
        mProgressAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                mWaveStopAnimator.start()
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationRepeat(p0: Animator?) {

            }
        }
        )
        mProgressAnimator.addUpdateListener {
            mOffestA += mWaveSpeedA
            mOffestB += mWaveSpeedB
            invalidate()
        }
        mProgressAnimator.start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0 && h > 0) {
            mWaveSpeedA = w / 10
            mWaveSpeedB = w / 17
            mWaveHeightA = context.dp2px(10F)
            mWaveHeightB = context.dp2px(5F)
            if (h / 10 < mWaveHeightA) {
                mWaveHeightA = h / 10
                mWaveHeightB = h / 20
            }
            initStopAnimator(mWaveHeightA, mWaveHeightB)
            mWaveCycleA = (3 * Math.PI / w).toFloat()
            mWaveCycleB = (4 * Math.PI / w).toFloat()

            val mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)

            val canvas: Canvas = Canvas(mBitmap)
            val ball: RectF = RectF(0F, 0F, w.toFloat(), h.toFloat())
            canvas.drawOval(ball, mWavePaint)
            mBallBitmap = mBitmap
        }
    }

    private fun initStopAnimator(waveHeightA: Int, waveHeightB: Int) {
        val holderA: PropertyValuesHolder = PropertyValuesHolder.ofInt("WaveHeightA", 0)
        val holderB: PropertyValuesHolder = PropertyValuesHolder.ofInt("WaveHeightB", 0)
        mWaveStopAnimator = ObjectAnimator.ofPropertyValuesHolder(this, holderA, holderB)
        mWaveStopAnimator.duration = 1000
        mWaveStopAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                isWaving = false
                mWaveHeightA = waveHeightA
                mWaveHeightB = waveHeightB
            }

            override fun onAnimationCancel(p0: Animator?) {
                mWaveHeightA = waveHeightA
                mWaveHeightB = waveHeightB
            }

            override fun onAnimationRepeat(p0: Animator?) {

            }
        })
        mWaveStopAnimator.addUpdateListener {
            mOffestA += mWaveSpeedA
            mOffestB += mWaveSpeedB
            invalidate()
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (height > 0 && width > 0 && canvas != null) {
            val paint: Paint = Paint()
            paint.color = Color.argb(60, 255, 182, 193)
            paint.style = Paint.Style.STROKE
            val edge: RectF = RectF(0F, 0F, width.toFloat(), height.toFloat())
            canvas.drawArc(edge, 0F, 360F, false, paint)
            canvas.drawColor(Color.TRANSPARENT)
            val sc: Int = canvas.saveLayer(
                0F,
                0F,
                width.toFloat(),
                height.toFloat(),
                null,
                Canvas.ALL_SAVE_FLAG
            )
            var index = 0
            if (isWaving) {
                for (i in 0..width) {
                    canvas.drawLine(
                        i.toFloat(),
                        getWaveY(i, mOffestA, mWaveHeightA, mWaveCycleA).toFloat(),
                        i.toFloat(),
                        height.toFloat(),
                        mWavePaint
                    )
                    canvas.drawLine(
                        i.toFloat(), getWaveY(i, mOffestA, mWaveHeightA, mWaveCycleA).toFloat(),
                        i.toFloat(),
                        height.toFloat(),
                        mWavePaint
                    )
                }
            }
            if (!isWaving) {
                val mHeight: Float = (1 - mProgress / 100.0F) * height
                canvas.drawRect(0F, mHeight, width.toFloat(), height.toFloat(), mWavePaint)
                canvas.drawRect(0F, mHeight, width.toFloat(), height.toFloat(), mWavePaint)
            }
            mWavePaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
            mBallBitmap?.let { canvas.drawBitmap(it, 0F, 0F, mWavePaint) }
            mWavePaint.xfermode = null
            canvas.restoreToCount(sc)
        }
    }

    private fun getWaveY(x: Int, offest: Int, waveHeight: Int, waveCycle: Float): Double {
        return waveHeight * sin(waveCycle * (x + offest)) + (1 - mProgress / 100.0) * height
    }

}