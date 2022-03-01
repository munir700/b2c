package com.yap.uikit.widgets.progressbar


import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.yap.uikit.R


class AnimatedProgressBar : LinearLayout, View.OnClickListener {

    private lateinit var leftImage: ImageView
    private lateinit var rightImage: ImageView
    private lateinit var progressBar: ProgressBar
    private var iconSize: Int = 80
    private var leftBackgroundResource: Int = -1
    private var leftBackgroundDrawable: Drawable? = null
    private var rightBackgroundDrawable: Drawable? = null
    private var progressValue: Int = 10
    private var rightBackgroundResource: Int = -1
    private var leftIconEnable: Boolean = true
    private var rightIconEnable: Boolean = true
    private var progressVisibility: Boolean = true
    private var imageLeftVisibility: Boolean = true
    private var imageRightVisibility: Boolean = true
    private var iconLeftMarginLeft: Int = 40
    private var iconLeftMarginRight: Int = 30
    private var iconRightMarginLeft: Int = 30
    private var iconRightMarginRight: Int = 40
    private var progressBarMargin: Int = 40
    private var iconsPadding: Int = 40
    private var progressBarHeight: Int = 15
    private var progressBarColor: Int = R.color.primary_blue
    private var progressBarBackgroundColor: Int = R.color.grey_dark
    private var progressCompleted: Boolean = false
    private var animationDuration: Int = 1

    private var listener: OnProgressWidgetButtonsClickListener? = null

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.AnimatedProgressBar)
            iconSize =
                typedArray.getDimensionPixelSize(
                    R.styleable.AnimatedProgressBar_apbIconSize,
                    iconSize
                )
            iconLeftMarginLeft = typedArray.getDimensionPixelSize(
                R.styleable.AnimatedProgressBar_apbLeftIconMarginLeft,
                iconLeftMarginLeft
            )
            iconLeftMarginRight = typedArray.getDimensionPixelSize(
                R.styleable.AnimatedProgressBar_apbLeftIconMarginRight,
                iconLeftMarginRight
            )
            iconRightMarginLeft = typedArray.getDimensionPixelSize(
                R.styleable.AnimatedProgressBar_apbRightIconMarginLeft,
                iconRightMarginLeft
            )
            iconRightMarginRight = typedArray.getDimensionPixelSize(
                R.styleable.AnimatedProgressBar_apbRightIconMarginRight,
                iconRightMarginRight
            )
            progressBarMargin = typedArray.getDimensionPixelSize(
                R.styleable.AnimatedProgressBar_apbProgressBarMarginBothSides,
                progressBarMargin
            )
            iconsPadding = typedArray.getDimensionPixelSize(
                R.styleable.AnimatedProgressBar_apbIconPadding,
                iconsPadding
            )
            progressBarHeight = typedArray.getDimensionPixelSize(
                R.styleable.AnimatedProgressBar_apbHeight,
                progressBarHeight
            )
            progressBarColor = typedArray.getInt(
                R.styleable.AnimatedProgressBar_apbColor,
                progressBarColor
            )
            progressBarBackgroundColor = typedArray.getInt(
                R.styleable.AnimatedProgressBar_apbBackgroundColor,
                progressBarBackgroundColor
            )
            animationDuration = typedArray.getInt(
                R.styleable.AnimatedProgressBar_apbDurationInSeconds,
                animationDuration
            )
            progressValue = typedArray.getInt(
                R.styleable.AnimatedProgressBar_apbProgress,
                1
            )

            leftBackgroundResource = typedArray.getInt(
                R.styleable.AnimatedProgressBar_apbLeftBackgroundResource,
                -1
            )
            rightBackgroundResource = typedArray.getInt(
                R.styleable.AnimatedProgressBar_apbRightBackgroundResource,
                -1
            )

            leftBackgroundDrawable = typedArray.getDrawable(
                R.styleable.AnimatedProgressBar_apbLeftBackgroundDrawable
            )
            rightBackgroundDrawable = typedArray.getDrawable(
                R.styleable.AnimatedProgressBar_apbRightBackgroundDrawable
            )

            leftIconEnable = typedArray.getBoolean(
                R.styleable.AnimatedProgressBar_apbLeftIconEnable,
                true
            )
            rightIconEnable = typedArray.getBoolean(
                R.styleable.AnimatedProgressBar_apbRightIconEnable,
                true
            )
            imageLeftVisibility = typedArray.getBoolean(
                R.styleable.AnimatedProgressBar_apbLeftImageVisibility,
                true
            )
            imageRightVisibility = typedArray.getBoolean(
                R.styleable.AnimatedProgressBar_apbRightImageVisibility,
                true
            )
            progressVisibility = typedArray.getBoolean(
                R.styleable.AnimatedProgressBar_apbVisibility,
                true
            )
            progressCompleted = typedArray.getBoolean(
                R.styleable.AnimatedProgressBar_apbProgressCompleted,
                false
            )
            typedArray.recycle()
        }

        leftImage = ImageView(context)
        rightImage = ImageView(context)
        progressBar = ProgressBar(
            context, null,
            android.R.attr.progressBarStyleHorizontal
        )


        orientation = HORIZONTAL
        gravity = Gravity.CENTER

        val paramsLeftImage =
            LayoutParams(
                iconSize,
                iconSize
            )
        val paramsProgressBar =
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                progressBarHeight
            )
        val paramsRightImage =
            LayoutParams(
                iconSize,
                iconSize
            )
        paramsProgressBar.weight = 1f

        paramsLeftImage.setMargins(iconLeftMarginLeft, 0, iconLeftMarginRight, 0)
        paramsRightImage.setMargins(iconRightMarginLeft, 0, iconRightMarginRight, 0)
        paramsProgressBar.setMargins(progressBarMargin, 0, progressBarMargin, 0)

        // set padding to the views
        setPaddingToViews()

        addView(leftImage, paramsLeftImage)
        addView(progressBar, paramsProgressBar)
        addView(rightImage, paramsRightImage)

        leftImage.setOnClickListener(this)
        rightImage.setOnClickListener(this)
        initializeViews()

    }

    private fun initializeViews() {
        // setting progressbar style like colors and rounded corners
        setStyleToProgressbar()
        setRightIconEnabled()
        setLeftIconEnabled()
        setProgressValue()
        setProgressBarVisibility()
        setLeftBackgroundResource()
        setLeftBackgroundResource()
        setLeftImageVisibility()
        setRightImageVisibility()
        setLeftBackgroundDrawable()
        setRightBackgroundDrawable()
        setProgressCompleted()
    }

    private fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    private fun initiateDoneBtnAnimation() {
        leftImage.visibility = View.INVISIBLE
        progressBar.visibility = View.INVISIBLE

        val screenCenterPoint: Float = getScreenWidth().toFloat()

        rightImage.animate()
            .translationX(0f)
            .translationXBy(-(screenCenterPoint - rightImage.width - (iconRightMarginRight * 2)) / 2)
            .translationY(0f)
            .setInterpolator(AccelerateInterpolator())
            .setDuration((animationDuration * 1000).toLong())
            .start()
        rightImage.isClickable = false
    }

    private fun setPaddingToViews() {
        leftImage.setPadding(iconsPadding, iconsPadding, iconsPadding, iconsPadding)
        rightImage.setPadding(iconsPadding, iconsPadding, iconsPadding, iconsPadding)
    }

    override fun onClick(veiw: View?) {
        when (veiw) {
            leftImage -> {
                listener?.onBackButtonClicked()
            }
            rightImage -> {
                listener?.onDoneButtonClicked()
            }
        }
    }


    private fun setStyleToProgressbar() {

        progressBar.max = 100
        val cornerSize: Float = (progressBarHeight + 8).toFloat()

        // Define a shape with rounded corners
        val roundedCorners = floatArrayOf(
            cornerSize,
            cornerSize,
            cornerSize,
            cornerSize,
            cornerSize,
            cornerSize,
            cornerSize,
            cornerSize
        )
        val pgDrawable = ShapeDrawable(RoundRectShape(roundedCorners, null, null))
        // Sets the progressBar foreground color
        pgDrawable.paint.color = progressBarColor as Int
        // Adds the drawable to your progressBar
        val progress = ClipDrawable(pgDrawable, Gravity.START, ClipDrawable.HORIZONTAL)
        progressBar.progressDrawable = progress


        // Adds background Color to the progressBar
        val pgDrawableBackground = ShapeDrawable(RoundRectShape(roundedCorners, null, null))
        pgDrawableBackground.paint.color = progressBarBackgroundColor as Int
        progressBar.background = pgDrawableBackground


    }

    fun setOnWidgetClickListener(listener: OnProgressWidgetButtonsClickListener) {
        this.listener = listener
    }

    interface OnProgressWidgetButtonsClickListener {
        fun onBackButtonClicked(){
            // to be handle later
        }
        fun onDoneButtonClicked(){
            // to be handle later
        }
    }

    var apbRightIconEnable: Boolean? = null
        set(value) {
            field = value
            apbRightIconEnable?.let {
                rightIconEnable = it
                setRightIconEnabled()
            }
        }

    var apbProgressCompleted: Boolean? = null
        set(value) {
            field = value
            apbProgressCompleted?.let {
                progressCompleted = it
                setProgressCompleted()
            }
        }

    var apbRightImageVisibility: Boolean? = null
        set(value) {
            field = value
            apbRightImageVisibility?.let {
                imageRightVisibility = it
                setRightImageVisibility()
            }
        }

    var apbLeftImageVisibility: Boolean? = null
        set(value) {
            field = value
            apbLeftImageVisibility?.let {
                imageLeftVisibility = it
                setLeftImageVisibility()
            }
        }

    var apbVisibility: Boolean? = null
        set(value) {
            field = value
            apbVisibility?.let {
                progressVisibility = it
                setProgressBarVisibility()
            }
        }

    var apbLeftIconEnable: Boolean? = null
        set(value) {
            field = value
            apbLeftIconEnable?.let {
                leftIconEnable = it
                setLeftIconEnabled()
            }
        }

    var apbProgress: Int? = null
        set(value) {
            field = value
            apbProgress?.let {
                progressValue = it
                setProgressValue()
            }
        }

    var apbLeftBackgroundResource: Int? = null
        set(value) {
            field = value
            apbLeftBackgroundResource?.let {
                leftBackgroundResource = it
                setLeftBackgroundResource()
            }
        }

    var apbRightBackgroundResource: Int? = null
        set(value) {
            field = value
            apbRightBackgroundResource?.let {
                rightBackgroundResource = it
                setRightBackgroundResource()
            }
        }

    var apbLeftBackgroundDrawable: Drawable? = null
        set(value) {
            field = value
            apbLeftBackgroundDrawable?.let {
                leftBackgroundDrawable = it
                setLeftBackgroundDrawable()
            }
        }

    var apbRightBackgroundDrawable: Drawable? = null
        set(value) {
            field = value
            apbRightBackgroundDrawable?.let {
                rightBackgroundDrawable = it
                setRightBackgroundDrawable()
            }
        }

    private fun setRightBackgroundDrawable() {
        rightBackgroundDrawable?.let {
            rightImage.background = it
        }
    }

    private fun setLeftBackgroundDrawable() {
        leftBackgroundDrawable?.let {
            leftImage.background = it
        }
    }

    private fun setRightBackgroundResource() {
        if (rightBackgroundResource != -1) {
            rightImage.setBackgroundResource(rightBackgroundResource)
        }
    }

    private fun setLeftBackgroundResource() {
        if (leftBackgroundResource != -1) {
            leftImage.setBackgroundResource(leftBackgroundResource)
        }
    }

    private fun setProgressValue() {
        progressBar.secondaryProgress = progressValue
    }

    private fun setLeftIconEnabled() {
        leftImage.isClickable = leftIconEnable
        leftImage.isEnabled = leftIconEnable
    }

    private fun setProgressBarVisibility() {
        progressBar.visibility = if (progressVisibility) View.VISIBLE else View.INVISIBLE
    }

    private fun setLeftImageVisibility() {
        leftImage.visibility = if (imageLeftVisibility) View.VISIBLE else View.INVISIBLE
    }

    private fun setRightImageVisibility() {
        rightImage.visibility = if (imageRightVisibility) View.VISIBLE else View.INVISIBLE
    }

    private fun setRightIconEnabled() {
        rightImage.isClickable = rightIconEnable
        rightImage.isEnabled = rightIconEnable

    }

    private fun setProgressCompleted() {
        if (progressCompleted) {
            initiateDoneBtnAnimation()
        }
    }
}