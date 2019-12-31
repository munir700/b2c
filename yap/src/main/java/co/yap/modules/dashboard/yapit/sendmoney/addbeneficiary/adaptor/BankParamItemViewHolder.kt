package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.adaptor

import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemBankParamsBinding
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.BankParamsItemViewModel
import co.yap.networking.customers.responsedtos.beneficiary.BankParams

class BankParamItemViewHolder(private val itemBankParamsBinding: ItemBankParamsBinding) :
    RecyclerView.ViewHolder(itemBankParamsBinding.root) {

    fun onBind(bankParams: BankParams, isLastIndex: Boolean, watcher: TextWatcher) {
        itemBankParamsBinding.viewModel = BankParamsItemViewModel(bankParams, 0, null)
        itemBankParamsBinding.executePendingBindings()
        itemBankParamsBinding.etBankName.addTextChangedListener(watcher)
        itemBankParamsBinding.etBankName.imeOptions =
            if (isLastIndex) EditorInfo.IME_ACTION_DONE else EditorInfo.IME_ACTION_NEXT
    }
}
