package co.yap.billpayments.mybills.adapter

import android.view.View
import co.yap.yapcore.interfaces.OnItemClickListener

class MyBillsItemViewModel(
    val myBillsModel: MyBillsModel?,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, myBillsModel!!, position)
    }
}
