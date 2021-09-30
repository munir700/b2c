package co.yap.modules.dashboard.home.component.categorybar

import android.animation.Animator
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.transition.TransitionManager
import co.yap.R
import co.yap.networking.transactions.responsedtos.categorybar.Categories
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.extentions.dimen
import com.liveperson.infra.utils.picasso.Picasso


class CustomCategoryBar(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private var parentView: View? = null
    private var constraintContainer: ConstraintLayout? = null
    private var segmentClickedListener: ISegmentClicked? = null
    private var singleCategory = false
    private var constraintArray = ArrayList<ConstraintLayout>(10)
    private val colorsArray = ArrayList<Int>()
    private var selectedDate: String = ""
    private val ids = arrayListOf<Int>()
    private val weights = arrayListOf<Float>()
    private var set: ConstraintSet? = null

    init {
        init()
        set = ConstraintSet()
        colorsArray.addAll(resources.getIntArray(R.array.analyticsColors).toTypedArray())
    }

    private fun init() {
        parentView =
            LayoutInflater.from(context).inflate(R.layout.category_segment_item, this, false)
        parentView?.let {
            constraintContainer = it.findViewById(R.id.container)
            constraintContainer?.let { linearContainerView ->
                linearContainerView.background =
                    ContextCompat.getDrawable(context, R.drawable.category_background)
                linearContainerView.clipToOutline = true
            }
        }
    }

    fun setCategoryBar(
        categorySegmentDataList: List<Categories>,
        mode: Int,
        date: String,
        isZero: Boolean,
        customCategoryBar: CustomCategoryBar
    ) {
        selectedDate = date
        //allow max 10 categories
        if (categorySegmentDataList.size <= 10) {
            singleCategory = categorySegmentDataList.isEmpty()
            //segment creation first time
            if (constraintArray.size == 0) {
                setCategorySegmentFirstTime(categorySegmentDataList)
                if (!isZero) {
                    setVisibilityWithAnimation(customCategoryBar, 1f)
                } else {
                    setVisibilityWithAnimation(customCategoryBar, 0f)
                }
            }
            //segment creation other than first time
            else {
                if (!isZero) {
                    setVisibilityWithAnimation(customCategoryBar, 1f)
                    if (!singleCategory) {
                        constraintContainer?.let { linearContainer ->
                            if (mode == Constants.COLLAPSE_MODE) {
                                setCollapseMode(linearContainer)
                            } else {
                                setExpandMode(linearContainer, categorySegmentDataList)
                            }
                            weights.clear()
                            for (i in 0..9) {
                                if (i >= categorySegmentDataList.size)
                                    weights.add(0f)
                                else
                                    weights.add(
                                        i,
                                        categorySegmentDataList[i].categoryWisePercentage
                                    )
                            }
                            //Reset the weights with animation
                            TransitionManager.beginDelayedTransition(constraintContainer!!)
                            set?.createHorizontalChain(
                                ConstraintSet.PARENT_ID,
                                ConstraintSet.LEFT,
                                ConstraintSet.PARENT_ID,
                                ConstraintSet.RIGHT,
                                ids.toIntArray(),
                                weights.toFloatArray(),
                                ConstraintSet.CHAIN_SPREAD_INSIDE
                            )
                            set?.applyTo(constraintContainer)
                        }
                    }
                } else {
                    setVisibilityWithAnimation(customCategoryBar, 0f)
                }
            }
        }
    }

    /**
     * Set default segments(first time)
     */
    private fun setCategorySegmentFirstTime(categorySegmentDataList: List<Categories>) {
        constraintContainer?.let { constraintContainer ->
            for (i in 0..9) {
                val constraintSegment = ConstraintLayout(context)
                val params = ConstraintLayout.LayoutParams(0, context.dimen(R.dimen._20sdp))
                params.setMargins(0, 0, 0, 0)
                constraintSegment.layoutParams = params
                constraintSegment.id = View.generateViewId()
                constraintSegment.setBackgroundColor(colorsArray[i % colorsArray.size])
                constraintContainer.addView(constraintSegment)
                ids.add(constraintSegment.id)
                if (i >= categorySegmentDataList.size)
                    weights.add(0f)
                else
                    weights.add(categorySegmentDataList[i].categoryWisePercentage)

                if (i == 0) {
                    if (categorySegmentDataList.isNotEmpty())
                        setFirstSegment(i, categorySegmentDataList[i], constraintSegment)
                    else
                    //we will create first segment with 0 in order to animate it
                        setFirstSegment(
                            0,
                            Categories(categoryWisePercentage = 0f, logoUrl = ""),
                            constraintSegment
                        )
                }
                constraintArray.add(i, constraintSegment)
            }
            //set weights by making horizontal chain
            set?.clone(constraintContainer)
            set?.createHorizontalChain(
                ConstraintSet.PARENT_ID,
                ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.RIGHT,
                ids.toIntArray(),
                weights.toFloatArray(),
                ConstraintSet.CHAIN_SPREAD_INSIDE
            )
            set?.applyTo(constraintContainer)
        }
        this.addView(parentView)
    }

    /**
     * Set segments in collapse mode
     */
    private fun setCollapseMode(linearContainer: ConstraintLayout) {
        linearContainer.animate()
            .scaleY(0.5f)
            .setInterpolator(AccelerateDecelerateInterpolator()).duration =
            300
        linearContainer.background =
            context.getDrawable(R.drawable.category_background_collapse)

        val layout: ConstraintLayout = constraintArray[0]
        var imageView: ImageView = layout.getChildAt(0) as ImageView
        var textView: TextView = layout.getChildAt(1) as TextView
        textView.visibility = View.GONE
        imageView.visibility = View.GONE
    }

    /**
     * Set segments in expand mode
     */
    private fun setExpandMode(
        linearContainer: ConstraintLayout,
        categorySegmentDataList: List<Categories>
    ) {
        linearContainer.animate()
            .scaleY(1f)
            .setInterpolator(AccelerateDecelerateInterpolator()).duration =
            300
        linearContainer.background =
            context.getDrawable(R.drawable.category_background)
        val layout: ConstraintLayout = constraintArray[0]
        var imageView: ImageView = layout.getChildAt(0) as ImageView
        var textView: TextView = layout.getChildAt(1) as TextView
        textView.text =
            "${categorySegmentDataList[0].categoryWisePercentage.toInt()}%"
        val urlString = categorySegmentDataList[0].logoUrl
        val uiHandler = Handler(Looper.getMainLooper())
        uiHandler.post {
            Picasso.get().load(urlString).into(imageView)
        }
        textView.visibility = View.VISIBLE
        imageView.visibility = View.VISIBLE
    }

    /**
     * Set first segment's decoration
     */
    private fun setFirstSegment(
        position: Int,
        categorySegmentData: Categories,
        linearLayout: ConstraintLayout
    ) {

        //add image
        var imageView = ImageView(context)
        imageView.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        val layoutParams =
            LayoutParams(
                context.dimen(R.dimen._15sdp),
                context.dimen(R.dimen._15sdp)
            )
        imageView.layoutParams = layoutParams
        val urlString = categorySegmentData.logoUrl
        if (urlString != "") {
            val uiHandler = Handler(Looper.getMainLooper())
            uiHandler.post {
                Picasso.get().load(urlString).into(imageView)
            }
        }
        imageView.id = View.generateViewId()
        linearLayout.addView(imageView, 0)

        //add textview
        val textView = TextView(context)
        val layoutParamsTextView =
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        textView.layoutParams = layoutParamsTextView

        textView.text = "${categorySegmentData.categoryWisePercentage.toInt()}%"
        textView.setTextAppearance(R.style.AMicroGrey)
        textView.setTextColor(Color.WHITE)
        textView.id = position
        linearLayout.addView(textView, 1)

        val constraintSet = ConstraintSet()
        constraintSet.clone(linearLayout)
        constraintSet.connect(
            imageView.id,
            ConstraintSet.START,
            linearLayout.id,
            ConstraintSet.START,
            context.dimen(R.dimen._3sdp)
        )
        constraintSet.centerVertically(imageView.id, linearLayout.id)
        constraintSet.connect(
            textView.id,
            ConstraintSet.START,
            imageView.id,
            ConstraintSet.END,
            context.dimen(R.dimen._3sdp)
        )
        constraintSet.centerVertically(textView.id, linearLayout.id)
        constraintSet.applyTo(linearLayout)
        if (categorySegmentData.categoryWisePercentage == 0f) {
            textView.visibility = View.GONE
            imageView.visibility = View.GONE
        } else {
            textView.visibility = View.VISIBLE
            imageView.visibility = View.VISIBLE
        }
    }

    /**
     * Set view visible/gone functionality with animation
     */
    private fun setVisibilityWithAnimation(customCategoryBar: CustomCategoryBar, animMode: Float) {
        customCategoryBar.animate()
            .scaleY(animMode)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                    if (animMode == 1f)
                        customCategoryBar.visibility = View.VISIBLE
                }

                override fun onAnimationEnd(p0: Animator?) {
                    if (animMode == 0f)
                        customCategoryBar.visibility = View.GONE
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }

            })
            .setInterpolator(AccelerateDecelerateInterpolator()).duration = 300

    }


    fun setSegmentClickedListener(segmentClickedListener: ISegmentClicked?) {
        this.segmentClickedListener = segmentClickedListener
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP) {
            segmentClickedListener?.onClickSegment(
                selectedDate
            )
        }
        return true
    }
}