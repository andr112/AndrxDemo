package com.zd.andrdemo.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.zd.andrdemo.R
import kotlin.math.min

/**
 * @功能描述:搜索历史记录，支持限制行数，展开和收起
 * @作者:Xixi
 */
class SearchHistoryView : View {
    private var mTextPaint: TextPaint
    private var mItemBgPaint: Paint
    private var mMaxlength: Int
    private var isLimit: Boolean
    private var mLimitLineCount: Int
    private var mHistories: ArrayList<String>? = null
    private var mAllLines: ArrayList<LineData>? = null
    private var mAllRects: HashMap<String, ItemPosition>? = null
    private var isOverFlow = false
    private var mMarginHorizontal: Int
    private var mMarginVertical: Int
    private var mItemPaddingHorizontal: Int
    private var mItemPaddingVertical: Int
    private var mListener: OnHistoryItemClick? = null
    private var mItemBgRadius: Int
    private var mOverFlowIconUp: Drawable?
    private var mOverFlowIconDown: Drawable?

    //private var mOverFlowIconWight: Float
    private val mOverFlowIconTag = " "

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SearchHistoryView)
        isLimit = a.getBoolean(R.styleable.SearchHistoryView_limit, false)
        mLimitLineCount = a.getInt(R.styleable.SearchHistoryView_limit_lines, Int.MAX_VALUE)

        mMaxlength = a.getInt(R.styleable.SearchHistoryView_text_max_length, 20)

        mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint.density = resources.displayMetrics.density
        mTextPaint.color = a.getColor(R.styleable.SearchHistoryView_text_color, Color.BLACK)
        mTextPaint.textSize = a.getDimension(R.styleable.SearchHistoryView_text_size, 16.0f)
        //mOverFlowIconWight = mTextPaint.measureText(mOverFlowIconTag)

        mItemBgPaint = Paint()
        mItemBgPaint.color =
            a.getColor(R.styleable.SearchHistoryView_item_background_color, Color.GRAY)
        mItemBgPaint.isAntiAlias = true

        mItemBgRadius =
            a.getDimensionPixelSize(R.styleable.SearchHistoryView_item_background_radius, 20)
        mMarginHorizontal =
            a.getDimensionPixelSize(R.styleable.SearchHistoryView_margin_horizontal, 10)
        mMarginVertical = a.getDimensionPixelSize(R.styleable.SearchHistoryView_margin_vertical, 10)
        mItemPaddingHorizontal =
            a.getDimensionPixelSize(R.styleable.SearchHistoryView_item_padding_horizontal, 10)
        mItemPaddingVertical =
            a.getDimensionPixelSize(R.styleable.SearchHistoryView_item_padding_vertical, 4)

        mOverFlowIconUp = a.getDrawable(R.styleable.SearchHistoryView_overFlowIconUp)
        mOverFlowIconDown = a.getDrawable(R.styleable.SearchHistoryView_overFlowIconDown)

        a.recycle()
    }

    fun isOverFlow(): Boolean {
        return isOverFlow
    }

    private fun setOverFlow(overFlow: Boolean) {
        isOverFlow = overFlow
    }

    fun isLimit(): Boolean {
        return isLimit
    }

    fun setLimit(limit: Boolean) {
        if (isLimit != limit) {
            isLimit = limit
            requestLayout()
            invalidate()
        }

    }

    fun setHistorys(datas: ArrayList<String>?) {
        mHistories = datas
        requestLayout()
        invalidate()
    }

    fun addHistory(data: String) {
        if (mHistories == null) {
            mHistories = ArrayList()
        }
        mHistories!!.add(0, data)
        requestLayout()
        invalidate()
    }

    fun setOnHistoryItemClicker(click: OnHistoryItemClick) {
        mListener = click
    }

    private fun getChildCount(): Int {
        return if (mHistories.isNullOrEmpty()) 0 else mHistories!!.size
    }

    private fun addLine(isFirst: Boolean): LineData {
        if (isFirst) {
            mAllLines = ArrayList()
        }
        val line = LineData(mAllLines?.size ?: 0)
        mAllLines?.add(line)
        return line
    }

    private fun measureChild(data: String): Rect {
        // mTextPaint.measureText(data)
        if (mAllRects == null) mAllRects = HashMap()
        if (mAllRects!!.contains(data)) {
            return mAllRects!!.get(data)!!.rect
        } else {
            val mBounds = Rect()
            mTextPaint.getTextBounds(data, 0, min(data.length, mMaxlength), mBounds)
            if (data == mOverFlowIconTag) {
                val icon = mOverFlowIconDown ?: mOverFlowIconUp
                icon?.let {
                    mBounds.right = icon.intrinsicWidth
                    // mBounds.bottom=icon.intrinsicHeight
                }
            }
            mBounds.right += 2 * mItemPaddingHorizontal
            mBounds.bottom += 2 * mItemPaddingVertical
            mAllRects?.put(data, ItemPosition(mBounds))
            return mBounds;
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val modeWidth = MeasureSpec.getMode(widthMeasureSpec)
        val sizeWidth = MeasureSpec.getSize(widthMeasureSpec)
        val modeHeight = MeasureSpec.getMode(heightMeasureSpec)
        val sizeHeight = MeasureSpec.getSize(heightMeasureSpec)

        val childCount = getChildCount()
        val contentTotalWidth = sizeWidth - paddingLeft - paddingRight

        var lineData = addLine(true)
        for (i in 0 until childCount) {
            val item: String = mHistories?.get(i) ?: continue
            lineData = addLineItem(item, contentTotalWidth, lineData)
        }
        val lineCount: Int = mAllLines?.size ?: 0
        setOverFlow(lineCount > mLimitLineCount)
        if (isOverFlow) {
            addLineItem(mOverFlowIconTag, contentTotalWidth, mAllLines!!.last())
        }
        var width = 0
        var height = 0
        for (i in 0 until lineCount) {
            lineData = mAllLines!![i]
            width = width.coerceAtLeast(lineData.width)
            if (isLimit && i >= mLimitLineCount) {
                break
            }
            height += lineData.height + mMarginVertical
        }

        // if (height >= mMargin) height -= mMargin
        setMeasuredDimension(
            if (modeWidth == MeasureSpec.EXACTLY) sizeWidth else width + paddingLeft + paddingRight,
            if (modeHeight == MeasureSpec.EXACTLY) sizeHeight else height + paddingTop + paddingBottom //
        )
    }

    private fun addLineItem(item: String, contentTotalWidth: Int, lineData: LineData): LineData {
        var line = lineData
        val rect = measureChild(item)
        val isAdded: Boolean =
            line.addView(item, contentTotalWidth, rect, mMarginHorizontal)
        if (!isAdded) {
            line = addLine(false)
            line.addView(item, contentTotalWidth, rect, mMarginHorizontal)
        }
        return line
    }

    override fun onDraw(canvas: Canvas) {
        // Draw the background for this view
        super.onDraw(canvas)
        // 设置子View的位置
        var left = paddingLeft
        var top = paddingTop
        // 需要绘制的行数
        var lineNumber: Int = mAllLines?.size ?: 0
        if (isLimit) {
            lineNumber = lineNumber.coerceAtMost(mLimitLineCount)
        }
        mAllRects?.values?.forEach {
            it.left = right
            it.top = height
        }
        var isOverFlowIconExtra = false
        for (i in 0 until lineNumber) {
            val lineData: LineData = mAllLines!![i]
            val lineViews = lineData.items ?: break
            for (j in lineViews.indices) {
                val isLimitedOverFlowLast =
                    i == lineNumber - 1 && j == lineViews.size - 1 && isOverFlow && isLimit
                if (isLimitedOverFlowLast) {
                    val contentWidth = width - paddingLeft - paddingRight
                    val isFlagReplace =
                        lineData.width + mMarginHorizontal + mAllRects!![mOverFlowIconTag]!!.rect.width() > contentWidth
                    if (isFlagReplace) {
                        left += drawItem(canvas, mOverFlowIconTag, lineData.height, left, top)
                    } else {
                        left += drawItem(canvas, lineViews[j], lineData.height, left, top)
                        left += drawItem(canvas, mOverFlowIconTag, lineData.height, left, top)
                    }
                } else {
                    left += drawItem(canvas, lineViews[j], lineData.height, left, top)
                }
            }
            left = paddingLeft
            top += lineData.height + mMarginVertical
        }
    }

    private fun drawItem(canvas: Canvas, data: String, lineHeight: Int, left: Int, top: Int): Int {
        val position = mAllRects!![data]!!
        position.left = left
        position.top = top
        val rect = position.rect
        val bg = RectF(
            left + rect.left.toFloat(),
            top.toFloat(),
            left + rect.right.toFloat(),
            top.toFloat() + lineHeight
        )
        val radius = mItemBgRadius.toFloat()
        canvas.drawRoundRect(bg, radius, radius, mItemBgPaint)
        if (data != mOverFlowIconTag)
            canvas.drawText(
                data, 0, data.length.coerceAtMost(mMaxlength),
                left.toFloat() + mItemPaddingHorizontal,
                top.toFloat() + lineHeight - mItemPaddingVertical,
                mTextPaint
            )
        when {
            data == mOverFlowIconTag && isLimit() -> mOverFlowIconDown
            data == mOverFlowIconTag -> mOverFlowIconUp
            else -> null
        }?.let {
            it.bounds = Rect(0, 0, it.intrinsicWidth, it.intrinsicHeight)
            canvas.save()
            canvas.translate(
                left.toFloat() + mItemPaddingHorizontal,
                top.toFloat() + mItemPaddingVertical
            )
            it.draw(canvas)
            canvas.restore()
        }
        return rect.width() + mMarginHorizontal
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
            }

            MotionEvent.ACTION_UP -> {
                val item = marchTouchItem(startX, startY, event.x, event.y)
                item?.let { mListener?.onClick(item, item == mOverFlowIconTag) }
            }
        }
        return true;
    }

    private var startX = 0.0f
    private var startY = 0.0f
    private fun marchTouchItem(startX: Float, startY: Float, x: Float, y: Float): String? {
        val item =
            mAllRects?.filter { (_, position) ->
                isPointInPosition(startX, startY, position) || isPointInPosition(x, y, position)
            }

        val datas = item?.keys

        return if (datas.isNullOrEmpty()) null else datas.last()
    }

    private fun isPointInPosition(x: Float, y: Float, position: ItemPosition): Boolean {
        val mgHorizontalHalf = mMarginHorizontal / 2
        val mgVerticalHalf = mMarginVertical / 2
        return position.left - mgHorizontalHalf < x && position.left + position.rect.width() + mgHorizontalHalf > x && position.top - mgVerticalHalf < y && position.top + position.rect.height() + mgVerticalHalf > y
    }

    internal class LineData(val lineNum: Int) {
        var items: MutableList<String>? = null
        var height = 0
        var width = 0
        fun addView(
            item: String,
            contentTotalWidth: Int,
            rect: Rect,
            margin: Int
        ): Boolean {
            val tempW = if (width == 0) width else width + margin
            val isAdd = rect.width() + tempW <= contentTotalWidth
            if (isAdd) {
                if (items == null) {
                    items = java.util.ArrayList()
                }
                width = rect.width() + tempW
                items!!.add(item)
                height = height.coerceAtLeast(rect.height())
            }
            return isAdd
        }

    }

    interface OnHistoryItemClick {
        fun onClick(item: String, isOverFlow: Boolean)
    }

    data class ItemPosition(val rect: Rect, var left: Int = 0, var top: Int = 0)

}