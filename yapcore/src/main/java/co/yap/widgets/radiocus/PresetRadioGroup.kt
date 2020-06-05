package co.yap.widgets.radiocus

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.IdRes
import co.yap.yapcore.R
import co.yap.yapcore.helpers.extentions.dimen
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import java.util.*


class PresetRadioGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {
    private var mCheckedId = View.NO_ID
    private var rangeSeekBar: RangeSeekBar? = null
    private var mSeekBarId = View.NO_ID
    private var mProtectFromCheckedChange = false
    var onCheckedChangeListener: OnCheckedChangeListener? = null
    private var defCheckedChangeListener: OnCheckedChangeListener? = null
    private val mChildViewsMap =
        HashMap<Int, View>()
    private var mPassThroughListener: PassThroughHierarchyChangeListener? =
        null
    private var mChildOnCheckedChangeListener: RadioCheckable.OnCheckedChangeListener? =
        null

    init {
        parseAttributes(attrs)
        setupView()
    }

    private fun parseAttributes(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.PresetRadioGroup, 0, 0
        )
        try {
            mCheckedId = a.getResourceId(
                R.styleable.PresetRadioGroup_presetRadioCheckedId,
                View.NO_ID
            )
            mSeekBarId = a.getResourceId(
                R.styleable.PresetRadioGroup_rangeSeekBarId,
                View.NO_ID
            )

        } finally {
            a.recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        rangeSeekBar = (parent as? View)?.findViewById(mSeekBarId)
        rangeSeekBar?.setProgress(30f)
        rangeSeekBar?.setOnRangeChangedListener(object : OnRangeChangedListener {
            override fun onRangeChanged(
                view: RangeSeekBar,
                leftValue: Float,
                rightValue: Float,
                isFromUser: Boolean
            ) {
                if (view.leftSeekBar.progress < 100) {
                    check(getChildAt(0).id)
                } else {
                    check(getChildAt(1).id)
                }
            }

            override fun onStartTrackingTouch(view: RangeSeekBar, isLeft: Boolean) {
                if (view.leftSeekBar.progress < 100) {
                    view.setProgress(30f)
                    check(getChildAt(0).id)
                } else {
                    view.setProgress(160f)
                    check(getChildAt(1).id)
                }
            }

            override fun onStopTrackingTouch(view: RangeSeekBar, isLeft: Boolean) {
                if (view.leftSeekBar.progress < 100) {
                    view.setProgress(30f)
                    check(getChildAt(0).id)
                } else {
                    view.setProgress(160f)
                    check(getChildAt(1).id)
                }
            }
        })
    }

    private fun setupView() {
        mChildOnCheckedChangeListener = CheckedStateTracker()
        mPassThroughListener = PassThroughHierarchyChangeListener()
        super.setOnHierarchyChangeListener(mPassThroughListener)
        defCheckedChangeListener = object : OnCheckedChangeListener {
            override fun onCheckedChanged(
                radioGroup: View?,
                radioButton: View?,
                isChecked: Boolean,
                checkedId: Int
            ) {

            }
        }
    }

    override fun addView(
        child: View,
        index: Int,
        params: ViewGroup.LayoutParams
    ) {
        if (child is RadioCheckable) {
            val button = child as RadioCheckable
            if (button.isChecked()) {
                mProtectFromCheckedChange = true
                if (mCheckedId != View.NO_ID) {
                    setCheckedStateForView(mCheckedId, false)
                }
                mProtectFromCheckedChange = false
                setCheckedId(child.id, true)
            }
        }
        super.addView(child, index, params)
    }

    override fun setOnHierarchyChangeListener(listener: OnHierarchyChangeListener) {
        mPassThroughListener?.mOnHierarchyChangeListener = listener
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (mCheckedId != View.NO_ID) {
            mProtectFromCheckedChange = true
            setCheckedStateForView(mCheckedId, true)
            mProtectFromCheckedChange = false
            setCheckedId(mCheckedId, true)
        }
    }


    private fun setCheckedId(@IdRes id: Int, isChecked: Boolean) {
        mCheckedId = id
        mChildViewsMap.values.forEach { v ->
            if (v is PresetValueButton) {
                v.elevation = context.dimen(R.dimen._1sdp).toFloat()
            }
        }
        if (mChildViewsMap[id] is PresetValueButton)
            mChildViewsMap[id]?.elevation = context.dimen(R.dimen._2sdp).toFloat()
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener?.onCheckedChanged(
                this,
                mChildViewsMap[id],
                isChecked,
                mCheckedId
            )
        }
        defCheckedChangeListener?.onCheckedChanged(
            this,
            mChildViewsMap[id],
            isChecked,
            mCheckedId
        )
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean {
        return p is LayoutParams
    }

    fun clearCheck() {
        check(View.NO_ID)
    }

    fun check(@IdRes id: Int) {
        if (id != View.NO_ID && id == mCheckedId) {
            return
        }
        if (mCheckedId != View.NO_ID) {
            setCheckedStateForView(mCheckedId, false)
        }
        if (id != View.NO_ID) {
            setCheckedStateForView(id, true)
        }
        setCheckedId(id, true)
    }

    private fun setCheckedStateForView(viewId: Int, checked: Boolean) {
        var checkedView: View?
        checkedView = mChildViewsMap[viewId]
        if (checkedView == null) {
            checkedView = findViewById(viewId)
            if (checkedView != null) {
                mChildViewsMap[viewId] = checkedView
            }
        }
        if (checkedView != null && checkedView is RadioCheckable) {
            (checkedView as RadioCheckable).setChecked(checked)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return LayoutParams(context, attrs)
    }

    interface OnCheckedChangeListener {
        fun onCheckedChanged(
            radioGroup: View?,
            radioButton: View?,
            isChecked: Boolean,
            checkedId: Int
        )
    }

    private inner class CheckedStateTracker :
        RadioCheckable.OnCheckedChangeListener {
        override fun onCheckedChanged(
            radioGroup: View?,
            isChecked: Boolean
        ) {
            if (mProtectFromCheckedChange) {
                return
            }
            mProtectFromCheckedChange = true
            if (mCheckedId != View.NO_ID) {
                setCheckedStateForView(mCheckedId, false)
            }
            mProtectFromCheckedChange = false
            val id = radioGroup?.id
            id?.let {
                setCheckedId(id, true)
            }

            if (!rangeSeekBar?.isPressed!!) {
                if (id == getChildAt(0).id) {
                    rangeSeekBar?.setProgress(30f)
                } else {
                    rangeSeekBar?.setProgress(160f)
                }
            }
        }
    }

    private inner class PassThroughHierarchyChangeListener :
        OnHierarchyChangeListener {
        var mOnHierarchyChangeListener: OnHierarchyChangeListener? =
            null

        /**
         * {@inheritDoc}
         */
        override fun onChildViewAdded(
            parent: View,
            child: View
        ) {
            if (parent === this@PresetRadioGroup && child is RadioCheckable) {
                var id = child.id
                if (id == View.NO_ID) {
                    id = View.generateViewId()
                    child.id = id
                }
                (child as RadioCheckable).addOnCheckChangeListener(
                    mChildOnCheckedChangeListener
                )
                mChildViewsMap[id] = child
            }
            mOnHierarchyChangeListener?.onChildViewAdded(parent, child)
        }

        /**
         * {@inheritDoc}
         */
        override fun onChildViewRemoved(
            parent: View,
            child: View
        ) {
            if (parent === this@PresetRadioGroup && child is RadioCheckable) {
                (child as RadioCheckable).removeOnCheckChangeListener(
                    mChildOnCheckedChangeListener
                )
            }
            mChildViewsMap.remove(child.id)
            mOnHierarchyChangeListener?.onChildViewRemoved(parent, child)
        }
    }
}
