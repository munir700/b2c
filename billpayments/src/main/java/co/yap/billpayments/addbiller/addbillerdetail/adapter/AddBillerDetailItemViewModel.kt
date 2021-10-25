package co.yap.billpayments.addbiller.addbillerdetail.adapter

import android.view.View
import co.yap.yapcore.interfaces.OnItemClickListener

class AddBillerDetailItemViewModel(
    val addBillerDetailInputFieldModel: AddBillerDetailInputFieldModel?,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, addBillerDetailInputFieldModel!!, position)
    }
}
