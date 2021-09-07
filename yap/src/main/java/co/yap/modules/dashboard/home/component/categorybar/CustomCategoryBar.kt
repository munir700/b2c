package co.yap.modules.dashboard.home.component.categorybar

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import co.yap.R
import co.yap.yapcore.helpers.extentions.dimen
import com.liveperson.infra.utils.picasso.Picasso

class CustomCategoryBar(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private var parentView: View? = null
    private var linearContainer: LinearLayout? = null
    private var segmentClickedListener: ISegmentClicked? = null
    private var singleCategory = false

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        parentView =
            LayoutInflater.from(context).inflate(R.layout.category_segment_item, this, false)
        parentView?.let {
            linearContainer = it.findViewById(R.id.container)
            linearContainer?.let { linearContainerView ->
                linearContainerView.gravity = Gravity.CENTER_VERTICAL
                linearContainerView.orientation = HORIZONTAL
                linearContainerView.weightSum = 100.0f
            }
        }
    }

    fun setCategoryPercent(categorySegmentDataList: ArrayList<CategorySegmentData>) {
        singleCategory = categorySegmentDataList.size == 0
        linearContainer?.let { linearContainer ->
            for (i in 0 until categorySegmentDataList.size) {
                val linearLayout = createSegmentLayout(categorySegmentDataList[i])
                when (i) {
                    0 -> {
                        setFirstSegment(categorySegmentDataList[i], linearLayout)
                    }
                    categorySegmentDataList.size - 1 -> {
                        setLastSegment(categorySegmentDataList[i], linearLayout)
                    }
                    else -> {
                        setCenterdSegments(categorySegmentDataList[i], linearLayout)
                    }
                }
                linearContainer.addView(linearLayout, i)
            }
        }
        this.removeAllViews()
        this.addView(parentView)
    }

    fun setSegmentClickedListener(segmentClickedListener: ISegmentClicked?) {
        this.segmentClickedListener = segmentClickedListener
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP) {
            segmentClickedListener!!.onClickSegment(
                3
            )
        }
        return true
    }

    /**
     * Create segment layout
     */
    private fun createSegmentLayout(categorySegmentData: CategorySegmentData): LinearLayout {
        val linearLayout = LinearLayout(context)
        linearLayout.orientation = HORIZONTAL
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, context.dimen(R.dimen._20sdp))
        params.gravity = Gravity.CENTER_VERTICAL
        params.weight = categorySegmentData.progress
        linearLayout.layoutParams = params
        return linearLayout
    }

    /**
     * Set segment's decoration
     */
    private fun setFirstSegment(
        categorySegmentData: CategorySegmentData,
        linearLayout: LinearLayout
    ) {
        //set shaped background to first segment
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        if (singleCategory)
            shape.cornerRadii = floatArrayOf(15f, 15f, 15f, 15f, 15f, 15f, 15f, 15f)
        else
            shape.cornerRadii = floatArrayOf(15f, 15f, 0f, 0f, 0f, 0f, 15f, 15f)

        shape.setColor(categorySegmentData.color)
        linearLayout.background = shape
        //add image
        var imageView = ImageView(context)
        imageView.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        val layoutParams =
            LayoutParams(
                context.dimen(R.dimen._15sdp),
                context.dimen(R.dimen._15sdp)
            )
        layoutParams.gravity = Gravity.CENTER
        layoutParams.marginStart = context.dimen(R.dimen._3sdp)
        imageView.layoutParams = layoutParams
        val urlString = categorySegmentData.icon
        val uiHandler = Handler(Looper.getMainLooper())
        uiHandler.post {
            Picasso.get().load(urlString).into(imageView)
            linearLayout.addView(imageView)
            //add textview
            var textView = TextView(context)
            val layoutParamsTextView =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            layoutParamsTextView.gravity = Gravity.CENTER
            layoutParamsTextView.marginStart = context.dimen(R.dimen._3sdp)
            textView.layoutParams = layoutParamsTextView
            textView.text = "60%"
            textView.setTextAppearance(R.style.AMicroGrey)
            textView.setTextColor(Color.WHITE)
            linearLayout.addView(textView)
        }
    }

    private fun setLastSegment(
        categorySegmentData: CategorySegmentData,
        linearLayout: LinearLayout
    ) {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadii = floatArrayOf(0f, 0f, 15f, 15f, 15f, 15f, 0f, 0f)
        shape.setColor(categorySegmentData.color)
        linearLayout.background = shape
    }

    private fun setCenterdSegments(
        categorySegmentData: CategorySegmentData,
        linearLayout: LinearLayout
    ) {
        linearLayout.setBackgroundColor(categorySegmentData.color)
    }
}