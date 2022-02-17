package co.yap.widgets.edittext

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatTextView

class TextViewRichDrawable : AppCompatTextView, DrawableEnriched {
    private var mRichDrawableHelper: RichDrawableHelper? = null

    constructor(context: Context) : super(context) {
        init(context, null, 0, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr, 0)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        mRichDrawableHelper = RichDrawableHelper(context, attrs!!, defStyleAttr, defStyleRes)
        mRichDrawableHelper?.apply(this)
    }

    /**
     * {@inheritDoc}
     */
    override fun getCompoundDrawableHeight(): Int {
        return mRichDrawableHelper!!.getCompoundDrawableHeight()
    }

    /**
     * {@inheritDoc}
     */
    override fun getCompoundDrawableWidth(): Int {
        return mRichDrawableHelper?.getCompoundDrawableWidth()?:0
    }

    override fun setDrawableStartVectorId(@DrawableRes id: Int) {
        mRichDrawableHelper?.setDrawableStartVectorId(id)
        mRichDrawableHelper?.apply(this)
    }

    override fun setDrawableEndVectorId(@DrawableRes id: Int) {
        mRichDrawableHelper?.setDrawableEndVectorId(id)
        mRichDrawableHelper?.apply(this)
    }

    override fun setDrawableTopVectorId(@DrawableRes id: Int) {
        mRichDrawableHelper?.setDrawableTopVectorId(id)
        mRichDrawableHelper?.apply(this)
    }

    override fun setDrawableBottomVectorId(@DrawableRes id: Int) {
        mRichDrawableHelper?.setDrawableBottomVectorId(id)
        mRichDrawableHelper?.apply(this)
    }
}
