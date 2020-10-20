package co.yap.modules.dashboard.yapit.addmoney

import android.view.View
import co.yap.yapcore.interfaces.OnItemClickListener


class YapItAddMoneyItemViewModel(
    val addMoneyOptions: AddMoneyOptions,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun handlePressOnView(view: View) {
        onItemClickListener?.onItemClick(view, addMoneyOptions, position)
    }
}