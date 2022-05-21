package co.yap.modules.dashboard.yapit.sendmoney.landing.viewmodels

import android.view.View
import co.yap.modules.dashboard.yapit.sendmoney.main.SendMoneyOptions
import co.yap.yapcore.interfaces.OnItemClickListener

@Deprecated(message = "Deprecating this class as we want to use view model extended with BaseListItemViewModel for latest recyclerview" +
        "implementation", replaceWith = ReplaceWith("SendMoneyLinearDashboardItemViewModel.kt"))
class SendMoneyDashboardItemViewModel(
    var sendMoneyOptions: SendMoneyOptions,
    var position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun handlePressOnView(view: View) {
        onItemClickListener?.onItemClick(view, sendMoneyOptions, position)
    }
}
