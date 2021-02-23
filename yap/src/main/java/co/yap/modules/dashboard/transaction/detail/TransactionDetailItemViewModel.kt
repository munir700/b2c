package co.yap.modules.dashboard.transaction.detail

import android.view.View
import co.yap.networking.transactions.responsedtos.transaction.ItemTransactionDetail
import co.yap.yapcore.interfaces.OnItemClickListener

class TransactionDetailItemViewModel(
    private val itemTransactionDetail: ItemTransactionDetail?,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
var item : ItemTransactionDetail? = null
init {
    item = itemTransactionDetail
}
    fun handlePressOnView(view: View) {
        onItemClickListener?.onItemClick(view, itemTransactionDetail!!, position)
    }
}