package co.yap.billpayments.payall.home.adapter

import android.view.View
import co.yap.networking.customers.responsedtos.billpayment.BillerInfoModel
import co.yap.yapcore.interfaces.OnItemClickListener

class OverlappingLogoItemViewModel(
    val billerInfoModel: BillerInfoModel,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, billerInfoModel, position)
    }
}
