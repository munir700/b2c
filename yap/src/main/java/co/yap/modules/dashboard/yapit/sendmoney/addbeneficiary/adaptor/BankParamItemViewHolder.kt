package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.adaptor

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemBankParamsBinding
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.BankParamsItemViewModel
import co.yap.networking.customers.responsedtos.beneficiary.BankParams

class BankParamItemViewHolder(private val itemBankParamsBinding: ItemBankParamsBinding) :
    RecyclerView.ViewHolder(itemBankParamsBinding.root) {

    fun onBind(bankParams: BankParams) {
        itemBankParamsBinding.viewModel = BankParamsItemViewModel(bankParams, 0, null)
        itemBankParamsBinding.executePendingBindings()
    }
}