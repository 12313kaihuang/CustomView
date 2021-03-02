package com.example.painttest.shader

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.painttest.R


class ShaderView(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    val lightingColorFilter = LightingColorFilter(0x00ffff, 0)
    private var type: Int = 0
    private val mPaint = Paint()
    private val mTextPaint = Paint()

    private val startColor: Int = Color.parseColor("#ff3333")
    private val endColor: Int = Color.parseColor("#2196F3")

    private val viewHeight: Int
        get() = measuredHeight - 50

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CanvasView)
        type = ta.getInt(R.styleable.CanvasView_cv_type, 0)
        ta.recycle()
        mTextPaint.color = Color.parseColor("#000000")
        mTextPaint.textSize = 42F
        mTextPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    fun setType(type: Int) {
        this.type = type;
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawCustom(canvas)
    }

    private fun drawCustom(canvas: Canvas?) {
        canvas?.let {
            when (type) {
                0 -> {
                    drawCircle(canvas)
                    drawText(canvas, "Linear-CLMP")
                }
                1 -> {
                    drawLinearGradient(canvas, Shader.TileMode.CLAMP)
                    drawText(canvas, "LinearGradient-CLMP")
                }
                2 -> {
                    drawLinearGradient(canvas, Shader.TileMode.MIRROR)
                    drawText(canvas, "LinearGradient-MIRROR")
                }
                3 -> {
                    drawLinearGradient(canvas, Shader.TileMode.REPEAT)
                    drawText(canvas, "LinearGradient-REPEAT")
                }
                4 -> {
                    drawRadiaGradient(canvas, Shader.TileMode.CLAMP)
                    drawText(canvas, "RadialGradient-CLMP")
                }
                5 -> {
                    drawRadiaGradient(canvas, Shader.TileMode.MIRROR)
                    drawText(canvas, "RadialGradient-MIRROR")
                }
                6 -> {
                    drawRadiaGradient(canvas, Shader.TileMode.REPEAT)
                    drawText(canvas, "RadialGradient-REPEAT")
                }
                7 -> {
                    drawSweepGradient(canvas)
                    drawText(canvas, "SweepGradient")
                }
                8 -> {
                    val bitmap = BitmapFactory.decodeResource(resources, R.drawable.images)
                    val shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
                    mPaint.shader = shader
                    val r = (bitmap.width / 2).toFloat()
                    //注意此时bitmap是从控件的0,0开始的  如果circle的原点换个位置画出来的就不是完整的头像了
                    canvas.drawCircle(r, r, r, mPaint)
                    drawText(canvas, "BitmapShader")
                }
                9 -> {
                    drawBitmapShader(canvas, Shader.TileMode.CLAMP)
                    drawText(canvas, "BitmapShader_CLMP")
                }
                10 -> {
                    drawBitmapShader(canvas, Shader.TileMode.MIRROR)
                    drawText(canvas, "BitmapShader_MIRROR")
                }
                11 -> {
                    drawBitmapShader(canvas, Shader.TileMode.REPEAT)
                    drawText(canvas, "BitmapShader_REPEAT")
                }
                12 -> {
                    val bitmap = BitmapFactory.decodeResource(resources, R.drawable.drag)
                    val shader1 = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
                    val bitmap2 = BitmapFactory.decodeResource(resources, R.drawable.images)
                    val shader2 =
                        BitmapShader(bitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
                    //DST SRC
                    val shader = ComposeShader(shader1, shader2, PorterDuff.Mode.SRC_IN)
                    val r = (bitmap2.width / 2).toFloat()
                    mPaint.shader = shader
                    canvas.drawCircle(r, r, r, mPaint)
                    drawText(canvas, "ComposeShader")
                }
            }
        }
    }

    private fun drawText(canvas: Canvas, text: String) {
        canvas.drawText(text, 0f, viewHeight.toFloat(), mTextPaint)
    }

    private fun drawCircle(canvas: Canvas) {
        val shader =
            LinearGradient(50f, 50f, 250f, 250f, startColor, endColor, Shader.TileMode.CLAMP)
        mPaint.shader = shader
        canvas.drawCircle(150f, 150f, 100f, mPaint)
    }

    private fun drawLinearGradient(canvas: Canvas, clamp: Shader.TileMode) {
        val height: Float = viewHeight.toFloat()
        val shader =
            LinearGradient(0f, 0f, height, height / 2, startColor, endColor, clamp)
        mPaint.shader = shader
        canvas.drawRect(Rect(0, 0, viewHeight * 2, viewHeight), mPaint)
    }

    private fun drawRadiaGradient(canvas: Canvas, clamp: Shader.TileMode) {
        val shader = RadialGradient(
            (viewHeight / 2).toFloat(),
            (viewHeight / 2).toFloat(),
            200f,
            startColor,
            endColor,
            clamp
        )
        mPaint.shader = shader
        canvas.drawRect(Rect(0, 0, measuredWidth, viewHeight), mPaint)
    }

    private fun drawSweepGradient(canvas: Canvas) {
        val shader = SweepGradient(150f, 150f, startColor, endColor)
        mPaint.shader = shader
        canvas.drawCircle(150f, 150f, 100f, mPaint)
    }

    private fun drawBitmapShader(canvas: Canvas, tileMode: Shader.TileMode) {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.images)
        val shader = BitmapShader(bitmap, tileMode, tileMode)
        mPaint.shader = shader
        canvas.drawRect(Rect(0, 0, measuredWidth, measuredHeight), mPaint)
    }
}