package co.yap.widgets.guidedtour

import co.yap.widgets.guidedtour.shape.Focus
import co.yap.widgets.guidedtour.shape.FocusGravity
import co.yap.widgets.guidedtour.utils.Constants


class DescriptionBoxConfiguration {
    var maskColor: Int
    var delayMillis: Long
    var isFadeAnimationEnabled: Boolean
    private var focusType: Focus
    private var focusGravity: FocusGravity
    var padding: Int
    var isDismissOnTouch: Boolean
    var colorTextViewInfo: Int
    var isDotViewEnabled: Boolean
    val isImageViewEnabled: Boolean

    fun getFocusType(): Focus {
        return focusType
    }

    fun setFocusType(focusType: Focus) {
        this.focusType = focusType
    }

    fun getFocusGravity(): FocusGravity {
        return focusGravity
    }

    fun setFocusGravity(focusGravity: FocusGravity) {
        this.focusGravity = focusGravity
    }

    init {
        maskColor = Constants.DEFAULT_MASK_COLOR
        delayMillis = Constants.DEFAULT_DELAY_MILLIS
        padding = Constants.DEFAULT_TARGET_PADDING
        colorTextViewInfo = Constants.DEFAULT_COLOR_TEXTVIEW_INFO
        focusType = Focus.ALL
        focusGravity = FocusGravity.CENTER
        isFadeAnimationEnabled = false
        isDismissOnTouch = false
        isDotViewEnabled = false
        isImageViewEnabled = true
    }
}