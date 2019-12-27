package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.adaptor

import android.text.TextWatcher
import androidx.databinding.ViewDataBinding
import co.yap.R
import co.yap.databinding.ItemBankParamsBinding
import co.yap.networking.customers.responsedtos.beneficiary.BankParams
import co.yap.yapcore.BaseBindingRecyclerAdapter


class AddBeneficiariesAdaptor(private val list: MutableList<BankParams>,private val textWatcher: TextWatcher) :
    BaseBindingRecyclerAdapter<BankParams, BankParamItemViewHolder>(list) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_bank_params

    override fun onCreateViewHolder(binding: ViewDataBinding): BankParamItemViewHolder {
        return BankParamItemViewHolder(binding as ItemBankParamsBinding)
    }

    override fun onBindViewHolder(holder: BankParamItemViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.onBind(list[position], list.size - 1 == position, textWatcher)

    }

}
