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
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import co.yap.widgets.guidedtour.Constants
import co.yap.widgets.guidedtour.MaterialIntroConfiguration
import co.yap.widgets.guidedtour.Utils
import co.yap.widgets.guidedtour.shape.*
import co.yap.widgets.guidedtour.shape.Rect
import co.yap.widgets.guidedtour.target.Target
import co.yap.widgets.guidedtour.target.ViewTarget
import co.yap.yapcore.R

class MaterialIntroView : RelativeLayout {
    /**
     * Mask color
     */
    private var maskColor = 0

    /**
     * MaterialIntroView will start
     * showing after delayMillis seconds
     * passed
     */
    private var delayMillis: Long = 0

    /**
     * We don't draw MaterialIntroView
     * until isReady field set to true
     */
    private var isReady = false

    /**
     * Show/Dismiss MaterialIntroView
     * with fade in/out animation if
     * this is enabled.
     */
    private var isFadeAnimationEnabled = false

    /**
     * Animation duration
     */
    private var fadeAnimationDuration: Long = 0

    /**
     * targetShape focus on target
     * and clear circle to focus
     */
    private var targetShape: Shape ?=null
//    private lateinit var targetShape: Shape
     /**
     * Focus Type
     */
    private var focusType: Focus? = null

    /**
     * FocusGravity type
     */
    private var focusGravity: FocusGravity? = null

    /**
     * Target View
     */
    private var targetView: Target? = null

    /**
     * Eraser
     */
    private var eraser: Paint? = null

    /**
     * Handler will be used to
     * delay MaterialIntroView
     */
    private var handler: Handler? = null

    /**
     * All views will be drawn to
     * this bitmap and canvas then
     * bitmap will be drawn to canvas
     */
    private var bitmap: Bitmap? = null
    private var canvas: Canvas? = null

    /**
     * Circle padding
     */
    private var padding = 0

    /**
     * Layout width/height
     */
    private var width = 0
    private var height = 0

    /**
     * Dismiss on touch any position
     */
    private var dismissOnTouch = false

    /**
     * Info dialog view
     */
    private var infoView: View? = null

    /**
     * Info Dialog Text
     */
    private var textViewInfo: TextView? = null

    /**
     * Info dialog text color
     */
    private var colorTextViewInfo = 0

    /**
     * Info dialog will be shown
     * If this value true
     */
    private var isInfoEnabled = false


    /**
     * Info Dialog Icon
     */
    private var imageViewIcon: ImageView? = null

    /**
     * Image View will be shown if
     * this is true
     */
    private var isImageViewEnabled = false

    /**
     * Save/Retrieve status of MaterialIntroView
     * If Intro is already learnt then don't show
     * it again.
     */
    private var preferencesManager: PreferencesManager? = null

    /**
     * Check using this Id whether user learned
     * or not.
     */
    private var materialIntroViewId: String? = null

    /**
     * When layout completed, we set this true
     * Otherwise onGlobalLayoutListener stuck on loop.
     */
    private var isLayoutCompleted = false

    /**
     * Notify user when MaterialIntroView is dismissed
     */
    private var materialIntroListener: MaterialIntroListener? = null

    /**
     * Perform click operation to target
     * if this is true
     */
    private var isPerformClick = false

    /**
     * Disallow this MaterialIntroView from showing up more than once at a time
     */
    private var isIdempotent = false

    /**
     * Shape of target
     */
    private var shapeType: ShapeType? = null

    /**
     * Use custom shape
     */
    private var usesCustomShape = false

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
        setWillNotDraw(false)
        visibility = View.INVISIBLE
        /**
         * set default values
         */
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
        isImageViewEnabled = true
        isIdempotent = false
        /**
         * initialize objects
         */
        handler = Handler()
        preferencesManager = PreferencesManager(context)
        eraser = Paint()
        eraser!!.color = -0x1
        eraser!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        eraser!!.flags = Paint.ANTI_ALIAS_FLAG
        val layoutInfo: View = LayoutInflater.from(getContext())
            .inflate(R.layout.material_intro_card, null)
        infoView = layoutInfo.findViewById(R.id.info_layout)
        textViewInfo =
            layoutInfo.findViewById<View>(R.id.textview_info) as TextView
        textViewInfo!!.setTextColor(colorTextViewInfo)
        imageViewIcon =
            layoutInfo.findViewById<View>(R.id.imageview_icon) as ImageView

        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                targetShape?.reCalculateAll()
                if (targetShape != null && targetShape?.point?.y !== 0 && !isLayoutCompleted) {
                    if (isInfoEnabled) setInfoLayout()
                     removeOnGlobalLayoutListener(
                        this@MaterialIntroView,
                        this
                    )
                }
            }
        })
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        width = measuredWidth
        height = measuredHeight
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!isReady) return
        if (bitmap == null || canvas == null) {
            if (bitmap != null) bitmap!!.recycle()
            bitmap = Bitmap.createBitmap(
                width,
                height,
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
     * Perform click operation when user
     * touches on target circle.
     *
     * @param event
     * @return
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val xT = event.x
        val yT = event.y
        val isTouchOnFocus: Boolean = targetShape.isTouchOnFocus(xT, yT)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (isTouchOnFocus && isPerformClick) {
                    targetView?.view?.setPressed(true)
                    targetView?.view?.invalidate()
                }
                return true
            }
            MotionEvent.ACTION_UP -> {
                if (isTouchOnFocus || dismissOnTouch) dismiss()
                if (isTouchOnFocus && isPerformClick) {
                    targetView?.view?.performClick()
                    targetView?.view?.setPressed(true)
                    targetView?.view?.invalidate()
                    targetView?.view?.setPressed(false)
                    targetView?.view?.invalidate()
                }
                return true
            }
            else -> {
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * Shows material view with fade in
     * animation
     *
     * @param activity
     */
    private fun show(activity: Activity) {
        if (preferencesManager.isDisplayed(materialIntroViewId)) return
        (activity.window.decorView as ViewGroup).addView(this)
        setReady(true)
        handler!!.postDelayed({
            if (isFadeAnimationEnabled) AnimationFactory.animateFadeIn(
                this@MaterialIntroView,
                fadeAnimationDuration,
                object : OnAnimationStartListener() {
                    fun onAnimationStart() {
                        visibility = View.VISIBLE
                    }
                }) else visibility = View.VISIBLE
        }, delayMillis)
        if (isIdempotent) {
            preferencesManager.setDisplayed(materialIntroViewId)
        }
    }

    /**
     * Dismiss Material Intro View
     */
    fun dismiss() {
        if (!isIdempotent) {
            preferencesManager.setDisplayed(materialIntroViewId)
        }
        AnimationFactory.animateFadeOut(
            this,
            fadeAnimationDuration,
            object : OnAnimationEndListener() {
                fun onAnimationEnd() {
                    visibility = View.GONE
                    removeMaterialView()
                    if (materialIntroListener != null) materialIntroListener.onUserClicked(
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
        handler!!.post {
            isLayoutCompleted = true
            if (infoView!!.parent != null) (infoView!!.parent as ViewGroup).removeView(
                infoView
            )
            val infoDialogParams =
                LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.FILL_PARENT
                )
            if (targetShape!!.point.y  < height/2) {
                (infoView as RelativeLayout?)!!.gravity = Gravity.TOP
                infoDialogParams.setMargins(
                    0,
                    targetShape!!.point.y + targetShape!!.height / 2,
                    0,
                    0
                )
            } else {
                (infoView as RelativeLayout?)!!.gravity = Gravity.BOTTOM
                infoDialogParams.setMargins(
                    0,
                    0,
                    0,
                    height - (targetShape!!.point.y + targetShape!!.height / 2) + 2 * targetShape!!.height / 2
                )
            }
            infoView!!.layoutParams = infoDialogParams
            infoView!!.postInvalidate()
            addView(infoView)
            if (!isImageViewEnabled) {
                imageViewIcon!!.visibility = View.GONE
            }
            infoView!!.visibility = View.VISIBLE
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
        textViewInfo!!.setTextColor(this.colorTextViewInfo)
    }

    private fun setTextViewInfo(textViewInfo: CharSequence) {
        this.textViewInfo!!.text = textViewInfo
    }

    private fun setTextViewInfoSize(textViewInfoSize: Int) {
        textViewInfo!!.setTextSize(
            TypedValue.COMPLEX_UNIT_SP,
            textViewInfoSize.toFloat()
        )
    }

    private fun enableInfoDialog(isInfoEnabled: Boolean) {
        this.isInfoEnabled = isInfoEnabled
    }

    private fun enableImageViewIcon(isImageViewEnabled: Boolean) {
        this.isImageViewEnabled = isImageViewEnabled
    }

    private fun setIdempotent(idempotent: Boolean) {
        isIdempotent = idempotent
    }



    fun setConfiguration(configuration: MaterialIntroConfiguration?) {
        if (configuration != null) {
            maskColor = configuration.getMaskColor()
            delayMillis = configuration.getDelayMillis()
            isFadeAnimationEnabled = configuration.isFadeAnimationEnabled()
            colorTextViewInfo = configuration.getColorTextViewInfo()
             dismissOnTouch = configuration.isDismissOnTouch()
            colorTextViewInfo = configuration.getColorTextViewInfo()
            focusType = configuration.getFocusType()
            focusGravity = configuration.getFocusGravity()
        }
    }

    private fun setUsageId(materialIntroViewId: String) {
        this.materialIntroViewId = materialIntroViewId
    }

    private fun setListener(materialIntroListener: MaterialIntroListener) {
        this.materialIntroListener = materialIntroListener
    }

    private fun setPerformClick(isPerformClick: Boolean) {
        this.isPerformClick = isPerformClick
    }

    /**
     * Builder Class
     */
    class Builder(private val activity: Activity) {
        private val materialIntroView: MaterialIntroView
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

        fun setInfoText(infoText: CharSequence): Builder {
            materialIntroView.enableInfoDialog(true)
            materialIntroView.setTextViewInfo(infoText)
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

        fun enableDotAnimation(isDotAnimationEnabled: Boolean): Builder {
            materialIntroView.enableDotView(isDotAnimationEnabled)
            return this
        }

        fun enableIcon(isImageViewIconEnabled: Boolean): Builder {
            materialIntroView.enableImageViewIcon(isImageViewIconEnabled)
            return this
        }

        fun setIdempotent(idempotent: Boolean): Builder {
            materialIntroView.setIdempotent(idempotent)
            return this
        }

        fun setConfiguration(configuration: MaterialIntroConfiguration?): Builder {
            materialIntroView.setConfiguration(configuration)
            return this
        }

        fun setListener(materialIntroListener: MaterialIntroListener): Builder {
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

        fun build(): MaterialIntroView {
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

        fun show(): MaterialIntroView {
            build().show(activity)
            return materialIntroView
        }

        init {
            materialIntroView = MaterialIntroView(activity)
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
}
