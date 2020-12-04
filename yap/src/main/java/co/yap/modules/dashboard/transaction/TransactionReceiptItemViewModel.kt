package co.yap.modules.dashboard.transaction

import android.view.View
import co.yap.yapcore.interfaces.OnItemClickListener

class TransactionReceiptItemViewModel(
    val itemReceipt: ReceiptModel,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun handlePressOnView(view: View) {
        onItemClickListener?.onItemClick(view, itemReceipt, position)
    }
}