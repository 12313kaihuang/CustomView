package com.example.painttest.patheffect

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class PathEffectView(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var type: Int = 0
    private val mPaths: Pair<Path, Path> by lazy { buildPath() }
    private val mPaint: Paint by lazy {
        Paint().also {
            it.style = Paint.Style.STROKE
            it.strokeWidth = 8f
        }
    }

    val typeText: String
        get() = when (type) {
            0 -> "CornerPathEffect"
            1 -> "DiscretePathEffect"
            2 -> "DashPathEffect"
            3 -> "PathDashPathEffect"
            4 -> "SumPathEffect"
            5 -> "ComposePathEffect"
            else -> "CornerPathEffect"
        }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            when (type) {
                0 -> drawCornerPathEffect(it)
                1 -> drawDiscretePathEffect(it)
                2 -> drawDashPathEffect(it)
                3 -> drawPathDashPathEffect(it)
                4 -> drawSumDashPathEffect(it)
                5 -> drawComposePathEffect(it)
                else -> drawCornerPathEffect(it)
            }
        }
    }

    private fun drawCornerPathEffect(canvas: Canvas) {
        canvas.drawPath(mPaths.first, mPaint)
        val pathEffect = CornerPathEffect(20f)
        mPaint.pathEffect = pathEffect
        canvas.drawPath(mPaths.second, mPaint)
    }

    private fun drawDiscretePathEffect(canvas: Canvas) {
        canvas.drawPath(mPaths.first, mPaint)
        mPaint.pathEffect = DiscretePathEffect(20f, 15f)
        canvas.drawPath(mPaths.second, mPaint)
    }

    private fun drawDashPathEffect(canvas: Canvas) {
        canvas.drawPath(mPaths.first, mPaint)
        mPaint.pathEffect = DashPathEffect(floatArrayOf(50f, 30f, 15f, 8f), 4f)
        canvas.drawPath(mPaths.second, mPaint)
    }

    private fun drawPathDashPathEffect(canvas: Canvas) {
        mPaint.strokeWidth = 5f
        val width = measuredWidth
        val height = measuredHeight
        val shape = Path().also {
            //it.addRoundRect(0f, 0f, 15f, 10f, 5f, 5f, Path.Direction.CW)
            it.moveTo(12f, 0f)
            it.lineTo(0f, 16f)
            it.lineTo(24f, 16f)
            it.close()
        }
        canvas.drawRoundRect(10f, 10f, width / 2 - 10f, height / 2 - 10f, 15f, 15f, mPaint)
        mPaint.pathEffect = PathDashPathEffect(shape, 40f, 0f, PathDashPathEffect.Style.TRANSLATE)
        canvas.drawRoundRect(width / 2 + 10f, 10f, width - 30f, height / 2 - 10f, 15f, 15f, mPaint)
        mPaint.pathEffect = PathDashPathEffect(shape, 40f, 0f, PathDashPathEffect.Style.ROTATE)
        canvas.drawRoundRect(10f, height / 2 + 20f, width / 2 - 20f, height - 20f, 15f, 15f, mPaint)
        mPaint.pathEffect = PathDashPathEffect(shape, 40f, 0f, PathDashPathEffect.Style.MORPH)
        canvas.drawRoundRect(
            width / 2 + 10f,
            height / 2 + 20f,
            width - 30f,
            height - 20f,
            15f,
            15f,
            mPaint
        )
    }

    private fun drawSumDashPathEffect(canvas: Canvas) {
        mPaint.strokeWidth = 5f
        val effect1 = DiscretePathEffect(20f, 15f)
        val effect2 = DashPathEffect(floatArrayOf(50f, 30f, 15f, 8f), 4f)
        mPaint.pathEffect = SumPathEffect(effect1, effect2)
        canvas.drawPath(mPaths.second, mPaint)
    }

    private fun drawComposePathEffect(canvas: Canvas) {
        val effect1 = DiscretePathEffect(20f, 15f)
        val effect2 = DashPathEffect(floatArrayOf(50f, 30f, 15f, 8f), 4f)
        mPaint.pathEffect = ComposePathEffect(effect1, effect2)
        canvas.drawPath(mPaths.second, mPaint)
    }

    private fun buildPath(): Pair<Path, Path> {
        val height = measuredHeight
        val mid = height / 2
        val width = (measuredWidth) / 6
        val originPath = Path().also {
            it.moveTo(10f, 10f)
            it.lineTo(10f + width, mid - 20f)
            it.lineTo(width * 2 - 20f, 30f)
            it.lineTo(30f + width * 3, mid - 40f)
            it.lineTo(10f + width * 4, 50f)
            it.lineTo(0f + width * 5, mid - 50f)
        }
        val effectPath = Path().also {
            val y = mid + 10f
            it.moveTo(10f, y)
            it.lineTo(10f + width, height - 20f)
            it.lineTo(width * 2 - 20f, y + 30f)
            it.lineTo(30f + width * 3, height - 40f)
            it.lineTo(10f + width * 4, y + 50f)
            it.lineTo(0f + width * 5, height - 50f)
        }
        return originPath to effectPath
    }

    fun setType(type: Int) {
        this.type = type
    }
}