package co.yap.billpayments.billerdetail.adapter

import android.view.View
import co.yap.yapcore.interfaces.OnItemClickListener

class BillerDetailItemViewModel(
    val billerDetailModel: BillerDetailModel?,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, billerDetailModel!!, position)
    }
}
