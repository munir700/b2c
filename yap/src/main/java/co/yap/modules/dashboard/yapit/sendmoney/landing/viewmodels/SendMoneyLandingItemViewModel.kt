package co.yap.modules.dashboard.yapit.sendmoney.landing.viewmodels

import android.view.View
import co.yap.modules.dashboard.yapit.addmoney.landing.AddMoneyLandingOptions
import co.yap.yapcore.interfaces.OnItemClickListener

class SendMoneyLandingItemViewModel(
    var addMoneyOptions: AddMoneyLandingOptions,
    var position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun handlePressOnView(view: View) {
        onItemClickListener?.onItemClick(view, addMoneyOptions, position)
    }
}
