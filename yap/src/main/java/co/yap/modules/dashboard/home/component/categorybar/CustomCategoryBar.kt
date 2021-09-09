package co.yap.modules.dashboard.home.component.categorybar

import android.animation.ObjectAnimator
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
    private var linearArray = ArrayList<LinearLayout>(10)
    private val colorsArray = ArrayList<Int>()

    init {
        init(attrs)
        colorsArray.addAll(resources.getIntArray(R.array.analyticsColors).toTypedArray())
    }

    private fun init(attrs: AttributeSet?) {
        parentView =
            LayoutInflater.from(context).inflate(R.layout.category_segment_item, this, false)
        parentView?.let {
            linearContainer = it.findViewById(R.id.containerLinear)
            linearContainer?.let { linearContainerView ->
                linearContainerView.gravity = Gravity.CENTER_VERTICAL
                linearContainerView.orientation = HORIZONTAL
                linearContainerView.weightSum = 100.0f
            }
        }
    }

    fun setCategorySegmentFirstTime(categorySegmentDataList: ArrayList<CategorySegmentData>) {
        singleCategory = categorySegmentDataList.size == 0
        linearContainer?.let { linearContainer ->
            for (i in 0 until categorySegmentDataList.size) {
                val linearLayout = createSegmentLayout(categorySegmentDataList[i])
                when (i) {
                    0 -> {
                        setFirstSegment(i, categorySegmentDataList[i], linearLayout)
                    }
                    categorySegmentDataList.size - 1 -> {
                        setLastSegment(i, linearLayout)
                    }
                    else -> {
                        setCenterdSegments(i, linearLayout)
                    }
                }
                linearContainer.addView(linearLayout, i)
            }
        }
        this.addView(parentView)
    }

    fun updateCategorySegment(categorySegmentDataList: ArrayList<CategorySegmentData>) {
        singleCategory = categorySegmentDataList.size == 0
        if (linearArray.size == 0) setCategorySegmentFirstTime(categorySegmentDataList)
        else {
            linearContainer?.let { linearContainer ->
                /*val params = linearContainer.layoutParams
                params.height = context.dimen(R.dimen._5sdp)
                linearContainer.layoutParams =params*/

                for (i in 0 until categorySegmentDataList.size) {
                    when (i) {
                        0 -> {
                            val animationWrapper = ViewWeightAnimationWrapper(linearArray[i])
                            val anim = ObjectAnimator.ofFloat(
                                animationWrapper,
                                "weight",
                                animationWrapper.weight,
                                categorySegmentDataList[i].progress
                            )
                            anim.duration = 500
                            anim.start()

                            val layout: LinearLayout = linearArray[i]
                            var textView: TextView = layout.getChildAt(1) as TextView
                            textView.text = categorySegmentDataList[i].progress.toString()
                        }
                        categorySegmentDataList.size - 1 -> {
                            val animationWrapper = ViewWeightAnimationWrapper(linearArray[i])
                            val anim = ObjectAnimator.ofFloat(
                                animationWrapper,
                                "weight",
                                animationWrapper.weight,
                                categorySegmentDataList[i].progress
                            )
                            anim.duration = 500
                            anim.start()
                        }
                        else -> {
                            val animationWrapper = ViewWeightAnimationWrapper(linearArray[i])
                            val anim = ObjectAnimator.ofFloat(
                                animationWrapper,
                                "weight",
                                animationWrapper.weight,
                                categorySegmentDataList[i].progress
                            )
                            anim.duration = 500
                            anim.start()
                        }
                    }
                }
            }
        }

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
        position: Int,
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

        shape.setColor(colorsArray[position % colorsArray.size])
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
        }
        linearLayout.addView(imageView, 0)
        //add textview
        val textView = TextView(context)
        val layoutParamsTextView =
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layoutParamsTextView.gravity = Gravity.CENTER
        layoutParamsTextView.marginStart = context.dimen(R.dimen._3sdp)
        textView.layoutParams = layoutParamsTextView
        textView.text = categorySegmentData.progress.toString()
        textView.setTextAppearance(R.style.AMicroGrey)
        textView.setTextColor(Color.WHITE)
        textView.id = position
        linearLayout.addView(textView, 1)
        linearArray.add(0, linearLayout)

    }

    private fun setLastSegment(
        position: Int,
        linearLayout: LinearLayout
    ) {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadii = floatArrayOf(0f, 0f, 15f, 15f, 15f, 15f, 0f, 0f)
        shape.setColor(colorsArray[position % colorsArray.size])
        linearLayout.background = shape
        linearArray.add(position, linearLayout)
    }

    private fun setCenterdSegments(
        position: Int,
        linearLayout: LinearLayout
    ) {
        linearLayout.setBackgroundColor(colorsArray[position % colorsArray.size])
        linearArray.add(position, linearLayout)
    }
}