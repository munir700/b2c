package co.yap.modules.dashboard.yapit.addmoney

import android.view.View
import co.yap.yapcore.interfaces.OnItemClickListener


class YapItAddMoneyItemVM(
    var addMoneyOptions: AddMoneyOptions,
    var position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun handlePressOnView(view: View) {
        onItemClickListener?.onItemClick(view, addMoneyOptions, position)
    }
}