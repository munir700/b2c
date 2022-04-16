package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.accountlist

import android.view.View
import co.yap.networking.leanteach.responsedtos.accountlistmodel.LeanCustomerAccounts
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel

class AccountChildItemViewModel(
    val leanCustomerAccounts: LeanCustomerAccounts,
    val bankListMainModel: BankListMainModel,
    val position: Int,
    private val onItemClickListener: OnItemClickListenerChild?
) {
    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, leanCustomerAccounts, bankListMainModel, position)
    }

    interface OnItemClickListenerChild {
        fun onItemClick(view: View, data: Any, bankListMainModel: BankListMainModel?, pos: Int)
    }
}