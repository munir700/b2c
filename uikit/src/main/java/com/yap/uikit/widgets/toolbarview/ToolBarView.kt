package com.yap.uikit.widgets.toolbarview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.appbar.MaterialToolbar
import com.yap.uikit.R
import com.yap.uikit.databinding.LayoutToolbarViewBinding

class ToolBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : MaterialToolbar(context, attrs), View.OnClickListener {
    private var viewBinding: LayoutToolbarViewBinding? = null
    private var startIconVisibility: Boolean = true
    private var startIconEnabled: Boolean = true
    private var endIconVisibility: Boolean = false
    private var endIconEnabled: Boolean = true
    private var endTextEnabled: Boolean = true
    private var endTextVisibility: Boolean = false
    private var bottomLineVisibility: Boolean = false
    private var startIconResource: Int = -1
    private var endIconResource: Int = -1
    private var bgResource: Int = -1
    private var startIconDrawable: Drawable? = null
    private var endIconDrawable: Drawable? = null
    private var bgDrawable: Drawable? = null
    private var endText: String? = null
    private var titleText: String? = null
    private var startIconMarginEnd: Int = 0
    private var startIconMarginStart: Int = 0
    private var endIconMarginStart: Int = 0
    private var endIconMarginEnd: Int = 0
    private var endTextMarginEnd: Int = 0
    private var endTextMarginStart: Int = 0

    private var listener: OnToolBarViewClickListener? = null

    init {
        initializeBinding()
        attrs?.let {
            val typedArray =
                context.obtainStyledAttributes(it, R.styleable.ToolbarView, 0, 0)
            startIconVisibility = typedArray.getBoolean(
                R.styleable.ToolbarView_tbStartIconVisibility,
                true
            )
            startIconEnabled = typedArray.getBoolean(
                R.styleable.ToolbarView_tbStartIconEnabled,
                true
            )
            endIconVisibility = typedArray.getBoolean(
                R.styleable.ToolbarView_tbEndIconVisibility,
                false
            )
            bottomLineVisibility = typedArray.getBoolean(
                R.styleable.ToolbarView_tbBottomLineVisibility,
                false
            )
            endIconEnabled = typedArray.getBoolean(
                R.styleable.ToolbarView_tbEndIconEnabled,
                true
            )
            endTextEnabled = typedArray.getBoolean(
                R.styleable.ToolbarView_tbEndTextEnabled,
                true
            )
            endTextVisibility = typedArray.getBoolean(
                R.styleable.ToolbarView_tbEndTextVisibility,
                false
            )
            startIconResource = typedArray.getResourceId(
                R.styleable.ToolbarView_tbStartIconResource,
                -1
            )
            endIconResource = typedArray.getResourceId(
                R.styleable.ToolbarView_tbEndIconResource,
                -1
            )
            bgResource = typedArray.getResourceId(
                R.styleable.ToolbarView_tbBackgroundResource,
                -1
            )
            startIconDrawable = typedArray.getDrawable(
                R.styleable.ToolbarView_tbStartIconDrawable
            )
            endIconDrawable = typedArray.getDrawable(
                R.styleable.ToolbarView_tbEndIconDrawable
            )
            bgDrawable = typedArray.getDrawable(
                R.styleable.ToolbarView_tbBackgroundDrawable
            )
            endText = typedArray.getString(
                R.styleable.ToolbarView_tbEndText
            )
            titleText = typedArray.getString(
                R.styleable.ToolbarView_tbTitleText
            )
            startIconMarginEnd = typedArray.getDimensionPixelSize(
                R.styleable.ToolbarView_tbStartIconMarginEnd,
                0
            )
            startIconMarginStart = typedArray.getDimensionPixelSize(
                R.styleable.ToolbarView_tbStartIconMarginStart,
                0
            )
            endIconMarginStart = typedArray.getDimensionPixelSize(
                R.styleable.ToolbarView_tbEndIconMarginStart,
                0
            )
            endIconMarginEnd = typedArray.getDimensionPixelSize(
                R.styleable.ToolbarView_tbEndIconMarginEnd,
                0
            )
            endTextMarginEnd = typedArray.getDimensionPixelSize(
                R.styleable.ToolbarView_tbEndTextMarginEnd,
                0
            )
            endTextMarginStart = typedArray.getDimensionPixelSize(
                R.styleable.ToolbarView_tbEndTextMarginStart,
                0
            )
            typedArray.recycle()
        }
        this.setContentInsetsAbsolute(0, 0)
        initializeValues()
    }

    private fun initializeValues() {
        setStartIconVisibility()
        setStartIconEnabled()
        setEndIconVisibility()
        setEndTextVisibility()
        setBottomLineVisibility()
        setEndIconEnabled()
        setEndTextEnabled()
        setStartIconResource()
        setStartIconDrawable()
        setEndIconDrawable()
        setEndIconResource()
        setBgResource()
        setBgDrawable()
        setEndText()
        setTitleText()
        setStartIconMargin()
        setEndIconMargin()
        setEndTextMargin()
        setClickListenerOnView()
    }

    private fun setClickListenerOnView() {
        viewBinding?.ivLeftIcon?.setOnClickListener(this)
        viewBinding?.ivRightIcon?.setOnClickListener(this)
        viewBinding?.tvRightText?.setOnClickListener(this)
        viewBinding?.toolbarTitle?.setOnClickListener(this)
    }

    private fun initializeBinding() {
        viewBinding = LayoutToolbarViewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    var tbStartIconVisibility: Boolean? = null
        set(value) {
            field = value
            tbStartIconVisibility?.let {
                startIconVisibility = it
                setStartIconVisibility()
            }
        }

    var tbEndIconVisibility: Boolean? = null
        set(value) {
            field = value
            tbEndIconVisibility?.let {
                endIconVisibility = it
                setEndIconVisibility()
            }
        }

    var tbEndTextVisibility: Boolean? = null
        set(value) {
            field = value
            tbEndTextVisibility?.let {
                endTextVisibility = it
                setEndTextVisibility()
            }
        }
    var tbBottomLineVisibility: Boolean? = null
        set(value) {
            field = value
            tbBottomLineVisibility?.let {
                bottomLineVisibility = it
                setBottomLineVisibility()
            }
        }

    var tbStartIconEnabled: Boolean? = null
        set(value) {
            field = value
            tbStartIconEnabled?.let {
                startIconEnabled = it
                setStartIconEnabled()
            }
        }

    var tbEndIconEnabled: Boolean? = null
        set(value) {
            field = value
            tbEndIconEnabled?.let {
                endIconEnabled = it
                setEndIconEnabled()
            }
        }

    var tbEndTextEnabled: Boolean? = null
        set(value) {
            field = value
            tbEndTextEnabled?.let {
                endTextEnabled = it
                setEndTextEnabled()
            }
        }

    var tbStartIconResource: Int? = null
        set(value) {
            field = value
            tbStartIconResource?.let {
                startIconResource = it
                setStartIconResource()
            }
        }

    var tbStartIconDrawable: Drawable? = null
        set(value) {
            field = value
            tbStartIconDrawable?.let {
                startIconDrawable = it
                setStartIconDrawable()
            }
        }

    var tbEndIconDrawable: Drawable? = null
        set(value) {
            field = value
            tbEndIconDrawable?.let {
                endIconDrawable = it
                setEndIconDrawable()
            }
        }

    var tbEndIconResource: Int? = null
        set(value) {
            field = value
            tbEndIconResource?.let {
                endIconResource = it
                setEndIconResource()
            }
        }

    var tbBackgroundResource: Int? = null
        set(value) {
            field = value
            tbBackgroundResource?.let {
                bgResource = it
                setBgResource()
            }
        }

    var tbBackgroundDrawable: Drawable? = null
        set(value) {
            field = value
            tbBackgroundDrawable?.let {
                bgDrawable = it
                setBgDrawable()
            }
        }
    var tbEndText: String? = null
        set(value) {
            field = value
            tbEndText?.let {
                endText = it
                setEndText()
            }
        }
    var tbTitleText: String? = null
        set(value) {
            field = value
            tbTitleText?.let {
                titleText = it
                setTitleText()
            }
        }

    var tbStartIconMarginEnd: Int? = null
        set(value) {
            field = value
            tbStartIconMarginEnd?.let {
                startIconMarginEnd = it
                setStartIconMargin()
            }
        }
    var tbStartIconMarginStart: Int? = null
        set(value) {
            field = value
            tbStartIconMarginStart?.let {
                startIconMarginStart = it
                setStartIconMargin()
            }
        }

    var tbEndIconMarginStart: Int? = null
        set(value) {
            field = value
            tbEndIconMarginStart?.let {
                endIconMarginStart = it
                setEndIconMargin()
            }
        }

    var tbEndIconMarginEnd: Int? = null
        set(value) {
            field = value
            tbEndIconMarginEnd?.let {
                endIconMarginEnd = it
                setEndIconMargin()
            }
        }

    var tbEndTextMarginEnd: Int? = null
        set(value) {
            field = value
            tbEndTextMarginEnd?.let {
                endTextMarginEnd = it
                setEndTextMargin()
            }
        }

    var tbEndTextMarginStart: Int? = null
        set(value) {
            field = value
            tbEndTextMarginStart?.let {
                endTextMarginStart = it
                setEndTextMargin()
            }
        }

    private fun setStartIconEnabled() {
        viewBinding?.ivLeftIcon?.isClickable = startIconEnabled
        viewBinding?.ivLeftIcon?.isEnabled = startIconEnabled
        viewBinding?.ivLeftIcon?.alpha = if (startIconEnabled) 1f else 0.5f
    }

    private fun setEndIconEnabled() {
        viewBinding?.ivRightIcon?.isClickable = endIconEnabled
        viewBinding?.ivRightIcon?.isEnabled = endIconEnabled
        viewBinding?.ivRightIcon?.alpha = if (endIconEnabled) 1f else 0.5f
    }

    private fun setEndTextEnabled() {
        viewBinding?.tvRightText?.isClickable = endTextEnabled
        viewBinding?.tvRightText?.isEnabled = endTextEnabled
        viewBinding?.tvRightText?.alpha = if (endTextEnabled) 1f else 0.5f
    }

    private fun setStartIconVisibility() {
        viewBinding?.ivLeftIcon?.visibility =
            if (startIconVisibility) View.VISIBLE else View.INVISIBLE
    }

    private fun setEndIconVisibility() {
        viewBinding?.ivRightIcon?.visibility =
            if (endIconVisibility) View.VISIBLE else View.INVISIBLE
    }

    private fun setEndTextVisibility() {
        viewBinding?.tvRightText?.visibility =
            if (endTextVisibility) View.VISIBLE else View.GONE
    }

    private fun setBottomLineVisibility() {
        viewBinding?.vBottomLine?.visibility =
            if (bottomLineVisibility) View.VISIBLE else View.GONE
    }

    private fun setStartIconResource() {
        if (startIconResource == -1) return
        viewBinding?.ivLeftIcon?.setImageResource(startIconResource)
    }

    private fun setStartIconDrawable() {
        startIconDrawable?.let {
            viewBinding?.ivLeftIcon?.setImageDrawable(it)
        }
    }

    private fun setEndIconDrawable() {
        endIconDrawable?.let {
            viewBinding?.ivRightIcon?.setImageDrawable(it)
        }
    }

    private fun setEndIconResource() {
        if (endIconResource == -1) return
        viewBinding?.ivRightIcon?.setImageResource(endIconResource)
    }

    private fun setBgResource() {
        if (bgResource == -1) return
        viewBinding?.tbMainToolBar?.setBackgroundResource(bgResource)
    }

    private fun setBgDrawable() {
        bgDrawable?.let {
            viewBinding?.tbMainToolBar?.background = it
        }
    }

    private fun setEndText() {
        endText?.let {
            viewBinding?.tvRightText?.text = it
        }
    }

    private fun setTitleText() {
        titleText?.let {
            viewBinding?.toolbarTitle?.text = it
        }
    }

    private fun setStartIconMargin() {
        val layout: ConstraintLayout.LayoutParams =
            viewBinding?.ivLeftIcon?.layoutParams as ConstraintLayout.LayoutParams
        layout.setMargins(startIconMarginStart, 0, startIconMarginEnd, 0)
        viewBinding?.ivLeftIcon?.layoutParams = layout
    }

    private fun setEndIconMargin() {
        val layout: ConstraintLayout.LayoutParams =
            viewBinding?.ivRightIcon?.layoutParams as ConstraintLayout.LayoutParams
        layout.setMargins(endIconMarginStart, 0, endIconMarginEnd, 0)
        viewBinding?.ivRightIcon?.layoutParams = layout
    }

    private fun setEndTextMargin() {
        val layout: ConstraintLayout.LayoutParams =
            viewBinding?.tvRightText?.layoutParams as ConstraintLayout.LayoutParams
        layout.setMargins(endTextMarginStart, 0, endTextMarginEnd, 0)
        viewBinding?.tvRightText?.layoutParams = layout
    }

    override fun onClick(v: View?) {
        when (v) {
            viewBinding?.ivLeftIcon -> {
                listener?.onStartIconClicked()
            }
            viewBinding?.ivRightIcon -> {
                listener?.onEndIconClicked()
            }
            viewBinding?.tvRightText -> {
                listener?.onEndTextClicked()
            }
            viewBinding?.toolbarTitle -> {
                listener?.onTitleClicked()
            }
        }
    }


    fun setOnToolBarViewClickListener(listener: OnToolBarViewClickListener) {
        this.listener = listener
    }

    interface OnToolBarViewClickListener {
        fun onStartIconClicked() {
            // to be handle later
        }

        fun onEndIconClicked() {
            // to be handle later
        }

        fun onEndTextClicked() {
            // to be handle later
        }

        fun onTitleClicked() {
            // to be handle later
        }
    }

}