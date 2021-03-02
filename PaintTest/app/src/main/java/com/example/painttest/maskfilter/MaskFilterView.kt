package com.example.painttest.maskfilter

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.painttest.R

class MaskFilterView(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var type: Int = 0
    private val bitmap: Bitmap
        get() = BitmapFactory.decodeResource(resources, R.drawable.unnamed)

    private val mPaint: Paint by lazy {
        Paint().also {
            it.style = Paint.Style.STROKE
            it.strokeWidth = 8f
        }
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.MaskFilterView)
        type = ta.getInt(R.styleable.MaskFilterView_type, 0)
        ta.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        setBackgroundColor(Color.parseColor("#DDDDDD"))
        canvas?.let {
            when (type) {
                0 -> drawNormal(it)
                1 -> drawBlurMaskFilter(canvas, BlurMaskFilter.Blur.NORMAL)
                2 -> drawBlurMaskFilter(canvas, BlurMaskFilter.Blur.SOLID)
                3 -> drawBlurMaskFilter(canvas, BlurMaskFilter.Blur.INNER)
                4 -> drawBlurMaskFilter(canvas, BlurMaskFilter.Blur.OUTER)
                5 -> drawEmbossMaskFilter(canvas)
                else -> drawNormal(it)
            }
        }
    }

    private fun drawNormal(canvas: Canvas) {
        canvas.drawBitmap(bitmap, 50f, 50f, mPaint)
    }

    private fun drawBlurMaskFilter(canvas: Canvas, style: BlurMaskFilter.Blur) {
        mPaint.maskFilter = BlurMaskFilter(50f, style)
        canvas.drawBitmap(bitmap, 50f, 50f, mPaint)
    }

    private fun drawEmbossMaskFilter(canvas: Canvas) {
        mPaint.maskFilter = EmbossMaskFilter(floatArrayOf(0f, 0f, 1f), 0.2f, 0.5f, 20f)
        canvas.drawBitmap(bitmap, 50f, 50f, mPaint)
    }
}