package co.yap.billpayments.billers.adapter

import android.view.View
import co.yap.yapcore.interfaces.OnItemClickListener

class BillerItemViewModel(
    val billerModel: BillerModel?,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, billerModel!!, position)
    }
}
