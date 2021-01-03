package co.yap.sendmoney.y2y.home.phonecontacts

import android.view.View
import co.yap.networking.customers.requestdtos.Contact
import co.yap.networking.customers.responsedtos.sendmoney.IBeneficiary
import co.yap.yapcore.interfaces.OnItemClickListener

class YapContactItemViewModel(
    val contact: IBeneficiary?,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {

    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, contact!!, position)
    }

}