package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.accountlist

import android.view.View
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel

class AccountGroupItemViewModel(
    val bankListMainModel: BankListMainModel,
    val position: Int,
    private val onItemClickListener: AccountChildItemViewModel.OnItemClickListenerChild?
) {
    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, bankListMainModel, null, position)
    }
}