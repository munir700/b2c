package co.yap.modules.location.tax

import android.view.View
import co.yap.networking.customers.requestdtos.Contact
import co.yap.yapcore.interfaces.OnItemClickListener

class TaxInfoItemViewModel(
    val taxModel: TaxModel?,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {

    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, taxModel!!, position)
    }

}