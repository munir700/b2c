package co.yap.yapcore.dagger.base.interfaces

import co.yap.yapcore.SingleClickEvent

interface OnClickHandler {
    val clickEvent: SingleClickEvent?
    fun handlePressOnView(id: Int)
}