package co.yap.modules.yapit.sendmoney.home.adapters

import android.view.View
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.interfaces.OnItemClickListener

class AdapterViewModel(
    val beneficiary: Beneficiary?,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {

    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, beneficiary!!, position)
    }

}