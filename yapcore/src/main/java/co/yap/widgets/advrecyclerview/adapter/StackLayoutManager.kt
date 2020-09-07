package co.yap.widgets.advrecyclerview.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewConfiguration
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnFlingListener
import androidx.recyclerview.widget.RecyclerView.Recycler
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

internal class StackLayoutManager() : RecyclerView.LayoutManager() {
    //the space unit for the stacked item
    private var mSpace = 60

    /**
     * the offset unit,deciding current position(the sum of  [.mItemWidth] and [.mSpace])
     */
    private var mUnit = 0

    //item width
    private var mItemWidth = 0
    private var mItemHeight = 0

    //the counting variable ,record the total offset including parallex
    private var mTotalOffset = 0

    //record the total offset without parallex
    private var mRealOffset = 0
    private var animator: ObjectAnimator? = null
    var animateValue = 0
        set(animateValue) {
            field = animateValue
            val dy = this.animateValue - lastAnimateValue
            fill(recycler, direction.layoutDirection * dy, false)
            lastAnimateValue = animateValue
        }
    private val duration = 300
    private var recycler: Recycler? = null
    private var lastAnimateValue = 0

    //the max stacked item count;
    private var maxStackCount = 4

    //initial stacked item
    private var initialStackCount = 4
    private var secondaryScale = 0.8f
    private var scaleRatio = 0.4f
    private var parallex = 1f
    private var initialOffset = 0
    private var initial = false
    private var mMinVelocityX = 0
    private val mVelocityTracker = VelocityTracker.obtain()
    private var pointerId = 0
    private var direction: Align =
        Align.LEFT
    private var mRV: RecyclerView? = null
    private var sSetScrollState: Method? = null
    private var mPendingScrollPosition = RecyclerView.NO_POSITION

    constructor(config: Config) : this() {
        maxStackCount = config.maxStackCount
        mSpace = config.space
        initialStackCount = config.initialStackCount
        secondaryScale = config.secondaryScale
        scaleRatio = config.scaleRatio
        direction = config.align
        parallex = config.parallex
    }

    override fun isAutoMeasureEnabled(): Boolean {
        return true
    }

    override fun onLayoutChildren(
        recycler: Recycler,
        state: RecyclerView.State
    ) {
        if (itemCount <= 0) return
        this.recycler = recycler
        detachAndScrapAttachedViews(recycler)
        //got the mUnit basing on the first child,of course we assume that  all the item has the same size
        val anchorView = recycler.getViewForPosition(0)
        measureChildWithMargins(anchorView, 0, 0)
        mItemWidth = anchorView.measuredWidth
        mItemHeight = anchorView.measuredHeight
        mUnit = if (canScrollHorizontally()) mItemWidth + mSpace else mItemHeight + mSpace
        //because this method will be called twice
        initialOffset = resolveInitialOffset()
        mMinVelocityX =
            ViewConfiguration.get(anchorView.context).scaledMinimumFlingVelocity
        fill(recycler, 0)
    }

    //we need take direction into account when calc initialOffset
    private fun resolveInitialOffset(): Int {
        var offset = initialStackCount * mUnit
        if (mPendingScrollPosition != RecyclerView.NO_POSITION) {
            offset = mPendingScrollPosition * mUnit
            mPendingScrollPosition = RecyclerView.NO_POSITION
        }
        if (direction == Align.LEFT) return offset
        if (direction == Align.RIGHT) return -offset
        return if (direction == Align.TOP) offset else offset
    }

    override fun onLayoutCompleted(state: RecyclerView.State) {
        super.onLayoutCompleted(state)
        if (itemCount <= 0) return
        if (!initial) {
            fill(recycler, initialOffset, false)
            initial = true
        }
    }

    override fun onAdapterChanged(
        oldAdapter: RecyclerView.Adapter<*>?,
        newAdapter: RecyclerView.Adapter<*>?
    ) {
        initial = false
        mRealOffset = 0
        mTotalOffset = mRealOffset
    }

    /**
     * the magic function :).all the work including computing ,recycling,and layout is done here
     *
     * @param recycler ...
     */
    private fun fill(recycler: Recycler?, dy: Int, apply: Boolean): Int {
        var delta = direction.layoutDirection * dy
        // multiply the parallex factor
        if (apply) delta = (delta * parallex).toInt()
        if (direction == Align.LEFT) return fillFromLeft(
            recycler,
            delta
        )
        if (direction == Align.RIGHT) return fillFromRight(
            recycler,
            delta
        )
        return if (direction == Align.TOP) fillFromTop(
            recycler,
            delta
        ) else dy //bottom alignment is not necessary,we don't support that
    }

    fun fill(recycler: Recycler?, dy: Int): Int {
        return fill(recycler, dy, true)
    }

    private fun fillFromTop(recycler: Recycler?, dy: Int): Int {
        if (mTotalOffset + dy < 0 || (mTotalOffset + dy + 0f) / mUnit > itemCount - 1) return 0
        recycler?.let { detachAndScrapAttachedViews(it) }
        mTotalOffset += direction.layoutDirection * dy
        val count = childCount
        //removeAndRecycle  views
        for (i in 0 until count) {
            val child = getChildAt(i)
            recycler?.let {
                child?.let { view ->
                    if (recycleVertically(child, dy)) removeAndRecycleView(
                        view,
                        it
                    )
                }
            }

        }
        val currPos = mTotalOffset / mUnit
        val leavingSpace = height - (left(currPos) + mUnit)
        val itemCountAfterBaseItem = leavingSpace / mUnit + 2
        val e = currPos + itemCountAfterBaseItem
        val start = if (currPos - maxStackCount >= 0) currPos - maxStackCount else 0
        val end = if (e >= itemCount) itemCount - 1 else e
        val left = width / 2 - mItemWidth / 2
        //layout views
        for (i in start..end) {
            val view = recycler?.getViewForPosition(i)
            view?.let {
                val scale = scale(i)
                val alpha = alpha(i)
                addView(it)
                measureChildWithMargins(it, 0, 0)
                val top = (left(i) - (1 - scale) * it.measuredHeight / 2).toInt()
                val right = it.measuredWidth + left
                val bottom = it.measuredHeight + top
                layoutDecoratedWithMargins(it, left, top, right, bottom)
                it.alpha = alpha
                it.scaleY = scale
                it.scaleX = scale
            }

        }
        return dy
    }

    private fun fillFromRight(recycler: Recycler?, dy: Int): Int {
        if (mTotalOffset + dy < 0 || (mTotalOffset + dy + 0f) / mUnit > itemCount - 1) return 0
        recycler?.let { detachAndScrapAttachedViews(it) }

        mTotalOffset += dy
        val count = childCount
        //removeAndRecycle  views
        for (i in 0 until count) {
            val child = getChildAt(i)
            recycler?.let {
                child?.let { view ->
                    if (recycleHorizontally(
                            child,
                            dy
                        )
                    ) removeAndRecycleView(view, it)
                }
            }
        }
        val currPos = mTotalOffset / mUnit
        val leavingSpace = left(currPos)
        val itemCountAfterBaseItem = leavingSpace / mUnit + 2
        val e = currPos + itemCountAfterBaseItem
        val start = if (currPos - maxStackCount <= 0) 0 else currPos - maxStackCount
        val end = if (e >= itemCount) itemCount - 1 else e

        //layout view
        for (i in start..end) {
            val view = recycler?.getViewForPosition(i)
            view?.let {
                val scale = scale(i)
                val alpha = alpha(i)
                addView(it)
                measureChildWithMargins(it, 0, 0)
                val left = (left(i) - (1 - scale) * view.measuredWidth / 2).toInt()
                val top = 0
                val right = left + it.measuredWidth
                val bottom = it.measuredHeight
                layoutDecoratedWithMargins(it, left, top, right, bottom)
                it.alpha = alpha
                it.scaleY = scale
                it.scaleX = scale
            }

        }
        return dy
    }

    private fun fillFromLeft(recycler: Recycler?, dy: Int): Int {
        if (mTotalOffset + dy < 0 || (mTotalOffset + dy + 0f) / mUnit > itemCount - 1) return 0
        recycler?.let { detachAndScrapAttachedViews(it) }
        mTotalOffset += direction.layoutDirection * dy
        val count = childCount
        //removeAndRecycle  views
        for (i in 0 until count) {
            val child = getChildAt(i)
            recycler?.let {
                child?.let { view ->
                    if (recycleHorizontally(
                            child,
                            dy
                        )
                    ) removeAndRecycleView(view, it)
                }
            }
        }
        val currPos = mTotalOffset / mUnit
        val leavingSpace = width - (left(currPos) + mUnit)
        val itemCountAfterBaseItem = leavingSpace / mUnit + 2
        val e = currPos + itemCountAfterBaseItem
        val start = if (currPos - maxStackCount >= 0) currPos - maxStackCount else 0
        val end = if (e >= itemCount) itemCount - 1 else e

        //layout view
        for (i in start..end) {
            val view = recycler?.getViewForPosition(i)
            view?.let {
                val scale = scale(i)
                val alpha = alpha(i)
                addView(it)
                measureChildWithMargins(it, 0, 0)
                val left = (left(i) - (1 - scale) * it.measuredWidth / 2).toInt()
                val top = 0
                val right = left + it.measuredWidth
                val bottom = top + it.measuredHeight
                layoutDecoratedWithMargins(it, left, top, right, bottom)
                it.alpha = alpha
                it.scaleY = scale
                it.scaleX = scale
            }
        }
        return dy
    }

    private val mTouchListener = OnTouchListener { v, event ->
        mVelocityTracker.addMovement(event)
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (animator != null && animator?.isRunning == true) animator?.cancel()
            pointerId = event.getPointerId(0)
        }
        if (event.action == MotionEvent.ACTION_UP) {
            if (v.isPressed) v.performClick()
            mVelocityTracker.computeCurrentVelocity(1000, 14000f)
            val xVelocity = mVelocityTracker.getXVelocity(pointerId)
            val o = mTotalOffset % mUnit
            val scrollX: Int
            if (Math.abs(xVelocity) < mMinVelocityX && o != 0) {
                scrollX = if (o >= mUnit / 2) mUnit - o else -o
                val dur = (Math.abs((scrollX + 0f) / mUnit) * duration).toInt()
                brewAndStartAnimator(dur, scrollX)
            }
        }
        false
    }
    private val mOnFlingListener: OnFlingListener = object : OnFlingListener() {
        override fun onFling(velocityX: Int, velocityY: Int): Boolean {
            val o = mTotalOffset % mUnit
            val s = mUnit - o
            val scrollX: Int
            val vel = absMax(velocityX, velocityY)
            scrollX = if (vel * direction?.layoutDirection > 0) {
                s
            } else -o
            val dur = computeSettleDuration(
                Math.abs(scrollX),
                Math.abs(vel).toFloat()
            )
            brewAndStartAnimator(dur, scrollX)
            setScrollStateIdle()
            return true
        }
    }

    private fun absMax(a: Int, b: Int): Int {
        return if (Math.abs(a) > Math.abs(b)) a else b
    }

    override fun onAttachedToWindow(view: RecyclerView) {
        super.onAttachedToWindow(view)
        mRV = view
        //check when raise finger and settle to the appropriate item
        view.setOnTouchListener(mTouchListener)
        view.onFlingListener = mOnFlingListener
    }

    private fun computeSettleDuration(distance: Int, xvel: Float): Int {
        val sWeight = 0.5f * distance / mUnit
        val velWeight: Float = if (xvel > 0) 0.5f * mMinVelocityX / xvel else 0.0f
        return ((sWeight + velWeight) * duration).toInt()
    }

    private fun brewAndStartAnimator(dur: Int, finalXorY: Int) {
        animator = ObjectAnimator.ofInt(this@StackLayoutManager, "animateValue", 0, finalXorY)
        animator?.setDuration(dur.toLong())
        animator?.start()
        animator?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                lastAnimateValue = 0
            }

            override fun onAnimationCancel(animation: Animator) {
                lastAnimateValue = 0
            }
        })
    }

    /******************************precise math method */
    private fun alpha(position: Int): Float {
        val alpha: Float
        val currPos = mTotalOffset / mUnit
        val n = (mTotalOffset + .0f) / mUnit
        alpha = if (position > currPos) 1.0f else {
            //temporary linear map,barely ok
            1 - (n - position) / maxStackCount
        }
        //for precise checking,oh may be kind of dummy
        return if (alpha <= 0.001f) 1.0f else 1.0f
    }

    private fun scale(position: Int): Float {
        return when (direction) {
            Align.LEFT, Align.RIGHT -> scaleDefault(
                position
            )
            else -> scaleDefault(position)
        }
    }

    private fun scaleDefault(position: Int): Float {
        val scale: Float
        val currPos = mTotalOffset / mUnit
        val n = (mTotalOffset + .0f) / mUnit
        val x = n - currPos
        // position >= currPos+1;
        scale = if (position >= currPos) {
            if (position == currPos) 1 - scaleRatio * (n - currPos) / maxStackCount else if (position == currPos + 1) //let the item's (index:position+1) scale be 1 when the item slide 1/2 mUnit,
            // this have better visual effect
            {
//                scale = 0.8f + (0.4f * x >= 0.2f ? 0.2f : 0.4f * x);
                secondaryScale + if (x > 0.5f) 1 - secondaryScale else 2 * (1 - secondaryScale) * x
            } else secondaryScale
        } else { //position <= currPos
            if (position < currPos - maxStackCount) 0f else {
                1f - scaleRatio * (n - currPos + currPos - position) / maxStackCount
            }
        }
        return 1.0f
    }

    /**
     * @param position the index of the item in the adapter
     * @return the accurate left position for the given item
     */
    private fun left(position: Int): Int {
        val currPos = mTotalOffset / mUnit
        val tail = mTotalOffset % mUnit
        val n = (mTotalOffset + .0f) / mUnit
        val x = n - currPos
        return when (direction) {
            Align.LEFT, Align.TOP ->                 //from left to right or top to bottom
                //these two scenario are actually same
                ltr(position, currPos, tail, x)
            Align.RIGHT -> rtl(
                position,
                currPos,
                tail,
                x
            )
            else -> ltr(position, currPos, tail, x)
        }
    }

    /**
     * @param position ..
     * @param currPos  ..
     * @param tail     .. change
     * @param x        ..
     * @return the left position for given item
     */
    private fun rtl(position: Int, currPos: Int, tail: Int, x: Float): Int {
        //虽然是做对称变换，但是必须考虑到scale给 对称变换带来的影响
        val scale = scale(position)
        val ltr = ltr(position, currPos, tail, x)
        return (width - ltr - mItemWidth * scale).toInt()
    }

    private fun ltr(position: Int, currPos: Int, tail: Int, x: Float): Int {
        var left: Int
        if (position <= currPos) {
            left = if (position == currPos) {
                (mSpace * (maxStackCount - x)).toInt()
            } else {
                (mSpace * (maxStackCount - x - (currPos - position))).toInt()
            }
        } else {
            left = if (position == currPos + 1) mSpace * maxStackCount + mUnit - tail else {
                val closestBaseItemScale = scale(currPos + 1)

                //调整因为scale导致的left误差
//                left = (int) (mSpace * maxStackCount + (position - currPos) * mUnit - tail
//                        -(position - currPos)*(mItemWidth) * (1 - closestBaseItemScale));
                val baseStart =
                    (mSpace * maxStackCount + mUnit - tail + closestBaseItemScale * (mUnit - mSpace) + mSpace).toInt()
                (baseStart + (position - currPos - 2) * mUnit - (position - currPos - 2) * (1 - secondaryScale) * (mUnit - mSpace)).toInt()
            }
            left = if (left <= 0) 0 else left
        }
        return left
    }

    /**
     * should recycle view with the given dy or say check if the
     * view is out of the bound after the dy is applied
     *
     * @param view ..
     * @param dy   ..
     * @return ..
     */
    private fun recycleHorizontally(
        view: View? /*int position*/,
        dy: Int
    ): Boolean {
        return view != null && (view.left - dy < 0 || view.right - dy > width)
    }

    private fun recycleVertically(view: View?, dy: Int): Boolean {
        return view != null && (view.top - dy < 0 || view.bottom - dy > height)
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: Recycler,
        state: RecyclerView.State
    ): Int {
        return fill(recycler, dx)
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: Recycler,
        state: RecyclerView.State
    ): Int {
        return fill(recycler, dy)
    }

    override fun canScrollHorizontally(): Boolean {
        return direction == Align.LEFT || direction == Align.RIGHT
    }

    override fun canScrollVertically(): Boolean {
        return direction == Align.TOP || direction == Align.BOTTOM
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    /**
     * we need to set scrollstate to [RecyclerView.SCROLL_STATE_IDLE] idle
     * stop RV from intercepting the touch event which block the item click
     */
    private fun setScrollStateIdle() {
        try {
            if (sSetScrollState == null) sSetScrollState =
                RecyclerView::class.java.getDeclaredMethod(
                    "setScrollState",
                    Int::class.javaPrimitiveType
                )
            sSetScrollState?.isAccessible = true
            sSetScrollState?.invoke(mRV, RecyclerView.SCROLL_STATE_IDLE)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
    }

    override fun scrollToPosition(position: Int) {
        if (position > itemCount - 1) {
            Log.i(
                TAG,
                "position is $position but itemCount is $itemCount"
            )
            return
        }
        val currPosition = mTotalOffset / mUnit
        val distance = (position - currPosition) * mUnit
        val dur = computeSettleDuration(Math.abs(distance), 0f)
        brewAndStartAnimator(dur, distance)
    }

    override fun requestLayout() {
        super.requestLayout()
        initial = false
    }

    interface CallBack {
        fun scale(totalOffset: Int, position: Int): Float
        fun alpha(totalOffset: Int, position: Int): Float
        fun left(totalOffset: Int, position: Int): Float
    }

    class Config {
        @IntRange(from = 2)
        var space = 60
        var maxStackCount = 3
        var initialStackCount = 0

        @FloatRange(from = 0.0, to = 1.0)
        var secondaryScale = 0f

        @FloatRange(from = 0.0, to = 1.0)
        var scaleRatio = 0f

        /**
         * the real scroll distance might meet requirement,
         * so we multiply a factor fro parallex
         */
        @FloatRange(from = 1.0, to = 2.0)
        var parallex = 1f
        var align: Align = Align.LEFT
        var applyAlpha = true
        var applyScale = true
    }

    internal enum class Align(var layoutDirection: Int) {
        LEFT(1), RIGHT(-1), TOP(1), BOTTOM(-1);

    }

    companion object {
        private const val TAG = "StackLayoutManager"
    }

    init {
        isAutoMeasureEnabled = true
    }
}