package co.yap.widgets.guidedtour.view

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.os.Build
import android.os.Handler
import android.util.AttributeSet
import android.util.TypedValue
import android.view.*
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import co.yap.widgets.guidedtour.DescriptionBoxConfiguration
import co.yap.widgets.guidedtour.DescriptionBoxListener
import co.yap.widgets.guidedtour.animation.AnimationFactory
import co.yap.widgets.guidedtour.animation.AnimationListener
import co.yap.widgets.guidedtour.description.CoachMarkInfoToolTip
import co.yap.widgets.guidedtour.description.CoachMarkSkipButton
import co.yap.widgets.guidedtour.description.Orientation
import co.yap.widgets.guidedtour.models.GuidedTourViewDetail
import co.yap.widgets.guidedtour.shape.*
import co.yap.widgets.guidedtour.shape.Rect
import co.yap.widgets.guidedtour.target.Target
import co.yap.widgets.guidedtour.target.ViewTarget
import co.yap.widgets.guidedtour.utils.Constants
import co.yap.widgets.guidedtour.utils.Utils
import co.yap.yapcore.R
import kotlinx.android.synthetic.main.view_description_box.view.*

class DescriptionView : RelativeLayout {

    private var maskColor = 0
    private var delayMillis: Long = 0
    private var isReady = false
    private var isFadeAnimationEnabled = false
    private var fadeAnimationDuration: Long = 0
    private var targetShape: Shape? = null
    private var focusType: Focus? = null
    private var focusGravity: FocusGravity? = null
    private var targetView: Target? = null
    private var eraser: Paint? = null
    private var taskHandler: Handler? = null
    private var bitmap: Bitmap? = null
    private var canvas: Canvas? = null
    private var padding = 0
    private var layoutWidth = 0
    private var layoutHeight = 0
    private var dismissOnTouch = false
    private var colorTextViewInfo = 0
    private var isInfoEnabled = false
    private var materialIntroViewId: String? = null
    private var isLayoutCompleted = false
    private var materialIntroListener: DescriptionBoxListener? = null
    private var isPerformClick = false
    private var isIdempotent = false
    private var shapeType: ShapeType? = null
    private var usesCustomShape = false

    private var mSkipButton: CoachMarkSkipButton? = null
    var mSkipButtonBuilder: CoachMarkSkipButton.Builder? = null
    fun getSkipButton(): CoachMarkSkipButton? = if (mSkipButtonBuilder == null) null else mSkipButtonBuilder?.build()
    fun getSkipButtonBuilder(): CoachMarkSkipButton.Builder? = mSkipButtonBuilder


    private var viewDataBinding: ViewDataBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_description_box,
        this,
        true
    )


    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    private fun init(context: Context) {
        viewDataBinding.executePendingBindings()

        setWillNotDraw(false)
        visibility = View.INVISIBLE

        maskColor = Constants.DEFAULT_MASK_COLOR
        delayMillis = Constants.DEFAULT_DELAY_MILLIS
        fadeAnimationDuration = Constants.DEFAULT_FADE_DURATION
        padding = Constants.DEFAULT_TARGET_PADDING
        colorTextViewInfo = Constants.DEFAULT_COLOR_TEXTVIEW_INFO
        focusType = Focus.ALL
        focusGravity = FocusGravity.CENTER
        shapeType = ShapeType.CIRCLE
        isReady = false
        isFadeAnimationEnabled = true
        dismissOnTouch = false
        isLayoutCompleted = false
        isInfoEnabled = false
        isPerformClick = false
        isIdempotent = false

        taskHandler = Handler()
        eraser = Paint()
        eraser!!.color = -0x1
        eraser!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        eraser!!.flags = Paint.ANTI_ALIAS_FLAG

        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                targetShape?.reCalculateAll()
                if (targetShape != null && targetShape?.point?.y !== 0 && !isLayoutCompleted) {
                    if (isInfoEnabled) setInfoLayout()
                    removeOnGlobalLayoutListener(
                        this@DescriptionView,
                        this
                    )
                }
            }
        })

        addSkipButton()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        layoutWidth = measuredWidth
        layoutHeight = measuredHeight
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!isReady) return
        if (bitmap == null || canvas == null) {
            if (bitmap != null) bitmap!!.recycle()
            bitmap = Bitmap.createBitmap(
                layoutWidth,
                layoutHeight,
                Bitmap.Config.ARGB_8888
            )
            this.canvas = Canvas(bitmap)
        }
        /**
         * Draw mask
         */
        this.canvas!!.drawColor(
            Color.TRANSPARENT,
            PorterDuff.Mode.CLEAR
        )
        this.canvas!!.drawColor(maskColor)
        /**
         * Clear focus area
         */
        targetShape?.draw(this.canvas, eraser, padding)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
    }

    /**
     * Shows material view with fade in
     * animation
     *
     * @param activity
     */
    private fun show(activity: Activity) {
        (activity.window.decorView as ViewGroup).addView(this)
        setReady(true)
        taskHandler!!.postDelayed({
            if (isFadeAnimationEnabled) AnimationFactory.animateFadeIn(
                this@DescriptionView,
                fadeAnimationDuration,
                object : AnimationListener.OnAnimationStartListener {
                    override fun onAnimationStart() {
                        visibility = View.VISIBLE
                    }
                }) else visibility = View.VISIBLE
        }, delayMillis)

    }

    /**
     * Dismiss Material Intro View
     */
    fun dismiss() {


        targetView?.view?.performClick()
        targetView?.view?.setPressed(true)
        targetView?.view?.invalidate()
        targetView?.view?.setPressed(false)
        targetView?.view?.invalidate()

        AnimationFactory.animateFadeOut(
            this,
            fadeAnimationDuration,
            object : AnimationListener.OnAnimationEndListener {
                override fun onAnimationEnd() {
                    visibility = View.GONE
                    removeMaterialView()
                    if (materialIntroListener != null) materialIntroListener?.onUserClicked(
                        materialIntroViewId
                    )
                }
            })
    }

    private fun removeMaterialView() {
        if (parent != null) (parent as ViewGroup).removeView(this)
    }

    /**
     * locate info card view above/below the
     * circle. If circle's Y coordiante is bigger than
     * Y coordinate of root view, then locate cardview
     * above the circle. Otherwise locate below.
     */
    private fun setInfoLayout() {
        var mToolTipBuilder: CoachMarkInfoToolTip.Builder? = CoachMarkInfoToolTip.Builder(context)
        fun getToolTip(): CoachMarkInfoToolTip? =
            if (mToolTipBuilder == null) null else mToolTipBuilder?.build()

        fun getToolTipBuilder(): CoachMarkInfoToolTip.Builder? = mToolTipBuilder



        taskHandler!!.post {
            isLayoutCompleted = true
            if (descriptionView!!.parent != null) (descriptionView!!.parent as ViewGroup).removeView(
                descriptionView
            )
            val infoDialogParams =
                LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            val tipParams =
                LayoutParams(
                    infoDialogParams.width,
                    infoDialogParams.height
                )

            if (targetShape!!.point.y < layoutHeight / 2) {
                (descriptionView as LinearLayout?)!!.gravity = Gravity.BOTTOM
                infoDialogParams.setMargins(
                    0,
                    targetShape!!.point.y + targetShape!!.height / 2,
                    0,
                    0
                )

                /*
          setting tip params here
         */
                tipParams.setMargins(
                    /*layoutWidth / 2*/ (targetShape!!.point.x - 16),
                    0,
                    0,
                    0
                )

            } else {
                (descriptionView as LinearLayout?)!!.gravity = Gravity.BOTTOM
                infoDialogParams.setMargins(
                    0,
                    targetShape!!.point.y - (targetShape!!.height),
                    0,
                    0
                )
                /*
               setting tip params here
              */
                tipParams.setMargins(
                    (targetShape!!.point.x - 16),
                    0,
                    0,
                    16
                )
                getToolTipBuilder()?.setToolTipOrientation(Orientation.DOWN)
            }

            descriptionView.layoutParams = infoDialogParams
            descriptionView.postInvalidate()
            addView(descriptionView)
            /*
             attaching tip here
            */
            var mTooltip: CoachMarkInfoToolTip? = getToolTip()
            mTooltip?.layoutParams = tipParams
            mTooltip!!.postInvalidate()
            (descriptionView as LinearLayout)?.addView(mTooltip)

            btnNext.setOnClickListener {

                dismiss()
            }
        }
    }

    /**
     * SETTERS
     */
    private fun setMaskColor(maskColor: Int) {
        this.maskColor = maskColor
    }

    private fun setDelay(delayMillis: Int) {
        this.delayMillis = delayMillis.toLong()
    }

    private fun enableFadeAnimation(isFadeAnimationEnabled: Boolean) {
        this.isFadeAnimationEnabled = isFadeAnimationEnabled
    }

    private fun setShapeType(shape: ShapeType) {
        shapeType = shape
    }

    private fun setReady(isReady: Boolean) {
        this.isReady = isReady
    }

    private fun setTarget(target: Target) {
        targetView = target
    }

    private fun setFocusType(focusType: Focus) {
        this.focusType = focusType
    }

    private fun setShape(shape: Shape) {
        targetShape = shape
    }

    private fun setPadding(padding: Int) {
        this.padding = padding
    }

    private fun setDismissOnTouch(dismissOnTouch: Boolean) {
        this.dismissOnTouch = dismissOnTouch
    }

    private fun setFocusGravity(focusGravity: FocusGravity) {
        this.focusGravity = focusGravity
    }

    private fun setColorTextViewInfo(colorTextViewInfo: Int) {
        this.colorTextViewInfo = colorTextViewInfo
        tvDescription.setTextColor(this.colorTextViewInfo)
    }

    fun setTotalViewsCount(currentViewCount: Int, totalViewsCount: Int) {
        tvCount.text = "$currentViewCount/$totalViewsCount"
    }


    private fun setTextViewInfo(guidedTourViewDetail: GuidedTourViewDetail) {
        tvTitle.text = guidedTourViewDetail.title
        tvDescription.text = guidedTourViewDetail.description
    }

    private fun setTextViewInfoSize(textViewInfoSize: Int) {
        tvDescription.setTextSize(
            TypedValue.COMPLEX_UNIT_SP,
            textViewInfoSize.toFloat()
        )
    }

    private fun enableInfoDialog(isInfoEnabled: Boolean) {
        this.isInfoEnabled = isInfoEnabled
    }

    private fun setIdempotent(idempotent: Boolean) {
        isIdempotent = idempotent
    }


    fun setConfiguration(configuration: DescriptionBoxConfiguration?) {
        if (configuration != null) {
            maskColor = configuration.maskColor
            delayMillis = configuration.delayMillis
            isFadeAnimationEnabled = configuration.isFadeAnimationEnabled
            colorTextViewInfo = configuration.colorTextViewInfo
            dismissOnTouch = configuration.isDismissOnTouch
            colorTextViewInfo = configuration.colorTextViewInfo
            focusType = configuration.getFocusType()
            focusGravity = configuration.getFocusGravity()
        }
    }

    private fun setUsageId(materialIntroViewId: String) {
        this.materialIntroViewId = materialIntroViewId
    }

    private fun setListener(materialIntroListener: DescriptionBoxListener) {
        this.materialIntroListener = materialIntroListener
    }

    private fun setPerformClick(isPerformClick: Boolean) {
        this.isPerformClick = isPerformClick
    }

    /**
     * Builder Class
     */
    class Builder(private val activity: Activity) {
        private val materialIntroView: DescriptionView
        private val focusType: Focus = Focus.MINIMUM
        fun setMaskColor(maskColor: Int): Builder {
            materialIntroView.setMaskColor(maskColor)
            return this
        }


        fun setDelayMillis(delayMillis: Int): Builder {
            materialIntroView.setDelay(delayMillis)
            return this
        }

        fun enableFadeAnimation(isFadeAnimationEnabled: Boolean): Builder {
            materialIntroView.enableFadeAnimation(isFadeAnimationEnabled)
            return this
        }

        fun setShape(shape: ShapeType): Builder {
            materialIntroView.setShapeType(shape)
            return this
        }

        fun setFocusType(focusType: Focus): Builder {
            materialIntroView.setFocusType(focusType)
            return this
        }

        fun setFocusGravity(focusGravity: FocusGravity): Builder {
            materialIntroView.setFocusGravity(focusGravity)
            return this
        }

        fun setTarget(view: View?): Builder {
            view?.let { ViewTarget(it) }?.let { materialIntroView.setTarget(it) }
            return this
        }

        fun setTargetPadding(padding: Int): Builder {
            materialIntroView.setPadding(padding)
            return this
        }

        fun setTextColor(textColor: Int): Builder {
            materialIntroView.setColorTextViewInfo(textColor)
            return this
        }

        fun setInfoText(guidedTourViewDetail: GuidedTourViewDetail): Builder {
            materialIntroView.enableInfoDialog(true)
            materialIntroView.setTextViewInfo(guidedTourViewDetail)
            return this
        }

        fun setViewCount(currentViewCount: Int, totalViewsCount: Int): Builder {
            materialIntroView.setTotalViewsCount(currentViewCount, totalViewsCount)
            return this
        }

        fun setInfoTextSize(textSize: Int): Builder {
            materialIntroView.setTextViewInfoSize(textSize)
            return this
        }

        fun dismissOnTouch(dismissOnTouch: Boolean): Builder {
            materialIntroView.setDismissOnTouch(dismissOnTouch)
            return this
        }

        fun setUsageId(materialIntroViewId: String): Builder {
            materialIntroView.setUsageId(materialIntroViewId)
            return this
        }

        fun setIdempotent(idempotent: Boolean): Builder {
            materialIntroView.setIdempotent(idempotent)
            return this
        }

        fun setConfiguration(configuration: DescriptionBoxConfiguration?): Builder {
            materialIntroView.setConfiguration(configuration)
            return this
        }

        fun setListener(materialIntroListener: DescriptionBoxListener): Builder {
            materialIntroView.setListener(materialIntroListener)
            return this
        }

        fun setCustomShape(shape: Shape): Builder {
            materialIntroView.usesCustomShape = true
            materialIntroView.setShape(shape)
            return this
        }

        fun performClick(isPerformClick: Boolean): Builder {
            materialIntroView.setPerformClick(isPerformClick)
            return this
        }

        fun build(): DescriptionView {
            if (materialIntroView.usesCustomShape) {
                return materialIntroView
            }

            // no custom shape supplied, build our own
            val shape: Shape
            if (materialIntroView.shapeType === ShapeType.CIRCLE) {
                shape = Circle(
                    materialIntroView.targetView,
                    materialIntroView.focusType,
                    materialIntroView.focusGravity,
                    materialIntroView.padding
                )
            } else {
                shape = Rect(
                    materialIntroView.targetView,
                    materialIntroView.focusType,
                    materialIntroView.focusGravity,
                    materialIntroView.padding
                )
            }
            materialIntroView.setShape(shape)
            return materialIntroView
        }

        fun show(): DescriptionView {
            build().show(activity)
            return materialIntroView
        }

        init {
            materialIntroView = DescriptionView(activity)
        }
    }

    companion object {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        fun removeOnGlobalLayoutListener(
            v: View,
            listener: ViewTreeObserver.OnGlobalLayoutListener?
        ) {
            if (Build.VERSION.SDK_INT < 16) {
                v.viewTreeObserver.removeGlobalOnLayoutListener(listener)
            } else {
                v.viewTreeObserver.removeOnGlobalLayoutListener(listener)
            }
        }
    }
    fun addSkipButton() {

        mSkipButton =  getSkipButton()
        mSkipButton?.let {
            if (indexOfChild(it) == -1) {
                addView(it)
                it.setOnClickListener {
             getSkipButtonBuilder()?.getButtonClickListener()?.onSkipButtonClick(this)
                }
                setSkipButton()
            }
        }
    }
    fun setSkipButton() {
        mSkipButton?.apply {
            val skipButtonParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            skipButtonParams.gravity =
                android.view.Gravity.TOP or android.view.Gravity.START or android.view.Gravity.LEFT
            val margin = getSkipButtonBuilder()!!.getButtonMargin()
            skipButtonParams.leftMargin = margin!!.left
            skipButtonParams.marginStart = margin!!.left
            try {
                skipButtonParams.topMargin = margin.top + Utils.getStatusBarHeight(context)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            layoutParams = skipButtonParams
        }
    }
}
