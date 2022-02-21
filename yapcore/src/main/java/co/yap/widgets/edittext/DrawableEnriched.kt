package co.yap.widgets.edittext

import androidx.annotation.DrawableRes

interface DrawableEnriched {
    /**
     * Returns the compound drawable width of this view.
     *
     * @return the width in pixels or {@value UNDEFINED} if undefined.
     */
    fun getCompoundDrawableHeight(): Int

    /**
     * Returns the compound drawable width of this view.
     *
     * @return the width in pixels or {@value UNDEFINED} if undefined.
     */
    fun getCompoundDrawableWidth(): Int
    fun setDrawableStartVectorId(@DrawableRes id: Int)
    fun setDrawableEndVectorId(@DrawableRes id: Int)
    fun setDrawableTopVectorId(@DrawableRes id: Int)
    fun setDrawableBottomVectorId(@DrawableRes id: Int)

    companion object {
        val UNDEFINED = -1
    }
}
