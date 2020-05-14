package co.yap.widgets.radiocus

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.IdRes
import co.yap.yapcore.R
import java.util.*

class PresetRadioGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {
    private var mCheckedId = View.NO_ID
    private var mProtectFromCheckedChange = false
    var onCheckedChangeListener: OnCheckedChangeListener? = null
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
        mCheckedId = try {
            a.getResourceId(
                R.styleable.PresetRadioGroup_presetRadioCheckedId,
                View.NO_ID
            )
        } finally {
            a.recycle()
        }
    }

    private fun setupView() {
        mChildOnCheckedChangeListener = CheckedStateTracker()
        mPassThroughListener = PassThroughHierarchyChangeListener()
        super.setOnHierarchyChangeListener(mPassThroughListener)
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
        // the user listener is delegated to our pass-through listener
        mPassThroughListener!!.mOnHierarchyChangeListener = listener
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        // checks the appropriate radio button as requested in the XML file
        if (mCheckedId != View.NO_ID) {
            mProtectFromCheckedChange = true
            setCheckedStateForView(mCheckedId, true)
            mProtectFromCheckedChange = false
            setCheckedId(mCheckedId, true)
        }
    }

    private fun setCheckedId(@IdRes id: Int, isChecked: Boolean) {
        mCheckedId = id
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener!!.onCheckedChanged(
                this,
                mChildViewsMap[id],
                isChecked,
                mCheckedId
            )
        }
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean {
        return p is LayoutParams
    }

    fun clearCheck() {
        check(View.NO_ID)
    }

    fun check(@IdRes id: Int) {
        // don't even bother
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
            buttonView: View?,
            isChecked: Boolean
        ) {
            // prevents from infinite recursion
            if (mProtectFromCheckedChange) {
                return
            }
            mProtectFromCheckedChange = true
            if (mCheckedId != View.NO_ID) {
                setCheckedStateForView(mCheckedId, false)
            }
            mProtectFromCheckedChange = false
            val id = buttonView!!.id
            setCheckedId(id, true)
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
                // generates an id if it's missing
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
