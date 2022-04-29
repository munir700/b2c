package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.accountlist

import co.yap.networking.leanteach.responsedtos.accountlistmodel.LeanCustomerAccounts
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel

data class AccountsListModel(
    val leanCustomerAccounts: LeanCustomerAccounts?,
    val bankListMainModel: BankListMainModel
)