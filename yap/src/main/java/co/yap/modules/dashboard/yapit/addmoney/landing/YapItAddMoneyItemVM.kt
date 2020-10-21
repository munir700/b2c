package co.yap.modules.dashboard.yapit.addmoney.landing

import android.view.View
import co.yap.modules.dashboard.yapit.addmoney.landing.AddMoneyOptions
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