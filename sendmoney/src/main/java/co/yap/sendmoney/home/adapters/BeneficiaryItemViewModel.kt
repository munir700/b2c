package co.yap.sendmoney.home.adapters

import android.view.View
import androidx.databinding.ObservableField
import co.yap.networking.customers.responsedtos.sendmoney.IBeneficiary
import co.yap.yapcore.interfaces.OnItemClickListener

class BeneficiaryItemViewModel(
    val beneficiary: IBeneficiary?,
    val position: Int,
    val sendMoneyType: String?,
    private val onItemClickListener: OnItemClickListener?, val isSearching: ObservableField<Boolean>
) {

    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, beneficiary!!, position)
    }

}