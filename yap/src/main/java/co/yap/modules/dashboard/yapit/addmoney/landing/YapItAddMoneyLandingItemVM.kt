package co.yap.modules.dashboard.yapit.addmoney.landing

import android.view.View
import co.yap.yapcore.interfaces.OnItemClickListener

@Deprecated(message = "Deprecating this class as we want to use view model extended with BaseListItemViewModel for latest recyclerview " +
        "implementation", replaceWith = ReplaceWith("AddMoneyLinearDashboardItemViewModel.kt"))
class YapItAddMoneyLandingItemVM(
    var addMoneyOptions: AddMoneyLandingOptions,
    var position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun handlePressOnView(view: View) {
        onItemClickListener?.onItemClick(view, addMoneyOptions, position)
    }
}