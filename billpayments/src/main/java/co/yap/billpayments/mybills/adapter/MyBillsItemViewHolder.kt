package co.yap.billpayments.mybills.adapter

import android.text.SpannableString
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.R
import co.yap.billpayments.databinding.ItemMyBillsBinding
import co.yap.yapcore.enums.BillStatus
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener

class MyBillsItemViewHolder(private val itemMyBillsBinding: ItemMyBillsBinding) :
    RecyclerView.ViewHolder(itemMyBillsBinding.root) {

    fun onBind(
        billModel: BillModel?,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        if (billModel?.billStatus == BillStatus.OVERDUE.title) {
            val amountSpan = SpannableString("${billModel.currency} ${billModel.amount}")
            itemMyBillsBinding.tvAmount.text = Utils.setSpan(
                0,
                billModel.currency.length,
                amountSpan,
                ContextCompat.getColor(
                    itemMyBillsBinding.tvAmount.context,
                    R.color.greyDark
                )
            )
        }
        itemMyBillsBinding.viewModel =
            MyBillsItemViewModel(billModel, position, onItemClickListener)
        itemMyBillsBinding.executePendingBindings()
    }
}
