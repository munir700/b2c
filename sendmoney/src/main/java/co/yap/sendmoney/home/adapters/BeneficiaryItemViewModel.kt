package co.yap.sendmoney.home.adapters

import android.view.View
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.customers.responsedtos.sendmoney.IBeneficiary
import co.yap.yapcore.interfaces.OnItemClickListener

class BeneficiaryItemViewModel(
    val beneficiary: IBeneficiary?,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {

    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, beneficiary!!, position)
    }

}