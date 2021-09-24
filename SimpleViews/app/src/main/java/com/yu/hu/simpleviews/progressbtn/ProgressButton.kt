package com.yu.hu.simpleviews.progressbtn

import android.content.Context
import android.graphics.*
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import com.yu.hu.simpleviews.R


/**
 * @auther hy
 * create on 2021/08/24 下午4:56
 *
 * 应用管理页面下载与进度按钮
 */
class ProgressButton(context: Context, attr: AttributeSet?, defStyleAttr: Int) :
    View(context, attr, defStyleAttr) {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)

    private val mPaint: Paint = newFillPaint()
    private val mTextPaint: Paint = newFillPaint()
    private var mBgRectF: RectF = RectF()

    private val mDataLock = Object()
    private var mPendingData = NOT_SET
    private var mDataInfo = NOT_SET

    private var mNormalTextColor: Int
    private var mNormalBgColor: Int
    private var mProgressTextColor: Int
    private var mProgressBgColor: Int
    private var mProgressHighlightColor: Int

    private var mBgCorner: Float
    private var mMaxProgress: Int = 100

    private val mText: String? get() = mDataInfo.text
    private val mCurProgress: Int get() = mDataInfo.progress
    private val mDisplayMode: DisplayMode get() = mDataInfo.displayMode
    private val mProgressPercent: Float get() = mDataInfo.progress * 1.0F / mMaxProgress

    init {
        mTextPaint.textAlign = Paint.Align.CENTER
        val typedValue = context.obtainStyledAttributes(attr, R.styleable.ProgressButton)
        mTextPaint.textSize = typedValue.getDimension(
            R.styleable.ProgressButton_pb_textSize, sp2px(
                DEFAULT_TEXT_SIZE
            )
        )
        mNormalTextColor =
            typedValue.getColor(R.styleable.ProgressButton_pb_textColor, DEFAULT_TEXT_COLOR)
        mNormalBgColor =
            typedValue.getColor(R.styleable.ProgressButton_pb_backgroundColor, DEFAULT_BG_COLOR)
        mBgCorner = typedValue.getDimension(
            R.styleable.ProgressButton_pb_cornerRadius, dp2px(
                DEFAULT_BG_CORNER
            )
        )
        mProgressTextColor = typedValue.getColor(
            R.styleable.ProgressButton_pb_progressTextColor,
            DEFAULT_PROGRESS_TEXT_COLOR
        )
        mProgressBgColor = typedValue.getColor(
            R.styleable.ProgressButton_pb_progressBackgroundColor,
            DEFAULT_PROGRESS_BG_COLOR
        )
        mProgressHighlightColor = typedValue.getColor(
            R.styleable.ProgressButton_pb_progressColor,
            DEFAULT_PROGRESS_HIGHLIGHT_COLOR
        )
        typedValue.recycle()
    }

    /**
     * 更新按钮文本
     *
     * [content] 按钮文本
     * [mode] [progress]用于根据不同mode绘制对应背景
     */
    fun setBtnText(content: String?, mode: DisplayMode = DisplayMode.NORMAL, progress: Int = 0) {
        var needInvalidate: Boolean
        synchronized(mDataLock) {
            needInvalidate = mPendingData === NOT_SET
            mPendingData = DateInfo(mode, content, progress)
        }
        Log.d(TAG, "setBtnText $needInvalidate $mode $content $progress")
        if (needInvalidate) postInvalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas == null) return
        synchronized(mDataLock) {
            if (mPendingData !== NOT_SET) {
                mDataInfo = mPendingData
            }
            mPendingData = NOT_SET
        }
        Log.d(TAG, "onDraw: $mDataInfo ")
        mBgRectF.set(0F, 0F, measuredWidth.toFloat(), measuredHeight.toFloat())
        drawBackground(canvas)
        drawProgress(canvas)
        drawText(canvas)
    }

    private fun drawBackground(canvas: Canvas) {
        mPaint.color = when (mDisplayMode) {
            DisplayMode.PROGRESS -> mProgressBgColor
            else -> mNormalBgColor
        }
        canvas.drawRoundRect(mBgRectF, mBgCorner, mBgCorner, mPaint)
    }

    private fun drawProgress(canvas: Canvas) {
        if (mCurProgress == 0 || mDisplayMode != DisplayMode.PROGRESS) return
//        LogUtil.d(TAG, "drawProgress $mCurProgress")
        val saveCount = canvas.save()
        val progressWidth = mBgRectF.width() * mProgressPercent
        canvas.clipRect(mBgRectF.left, mBgRectF.top, progressWidth, mBgRectF.bottom)
        mPaint.color = mProgressHighlightColor
        canvas.drawRoundRect(mBgRectF, mBgCorner, mBgCorner, mPaint)
        canvas.restoreToCount(saveCount)
    }

    private fun drawText(canvas: Canvas) {
        if (TextUtils.isEmpty(mText)) return
//        LogUtil.d(TAG, "drawText $mText")
        mTextPaint.color = when (mDisplayMode) {
            DisplayMode.PROGRESS -> mProgressTextColor
            else -> mNormalTextColor
        }
        val fontMetrics = mTextPaint.getFontMetrics()
        val top = fontMetrics.top
        val bottom = fontMetrics.bottom
        val baseLineY = (mBgRectF.centerY() - top / 2 - bottom / 2) //基线中间点的y值
        canvas.drawText(mText ?: "", mBgRectF.centerX(), baseLineY, mTextPaint)
    }

    @Suppress("SameParameterValue")
    private fun dp2px(dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        )
    }

    @Suppress("SameParameterValue")
    private fun sp2px(sp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp,
            context.resources.displayMetrics
        )
    }

    private fun newFillPaint(): Paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    companion object {
        const val TAG = "ProgressButton"

        val NOT_SET = DateInfo()

        private val DEFAULT_TEXT_COLOR = Color.rgb(0, 0, 0)
        private val DEFAULT_BG_COLOR = Color.parseColor("#F0F0F0")//Color.argb(15, 0, 0, 0)
        private const val DEFAULT_BG_CORNER = 100F //dp
        private const val DEFAULT_TEXT_SIZE = 12F //sp

        private val DEFAULT_PROGRESS_TEXT_COLOR = Color.rgb(255, 97, 46)
        private val DEFAULT_PROGRESS_BG_COLOR =
            Color.parseColor("#FFF6F2")//Color.argb(15, 255, 97, 46)
        private val DEFAULT_PROGRESS_HIGHLIGHT_COLOR =
            Color.parseColor("#FFD8CB")//Color.argb(51, 255, 97, 46)
    }

    sealed class DisplayMode {
        object NORMAL : DisplayMode()
        object PROGRESS : DisplayMode()
    }

    data class DateInfo(val displayMode: DisplayMode, val text: String?, val progress: Int) {
        constructor() : this(DisplayMode.NORMAL, null, 0)
    }
}