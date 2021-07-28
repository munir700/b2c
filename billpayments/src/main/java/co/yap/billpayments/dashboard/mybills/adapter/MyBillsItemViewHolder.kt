package co.yap.billpayments.dashboard.mybills.adapter

import android.text.SpannableString
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.R
import co.yap.billpayments.databinding.LayoutItemMyBillsBinding
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.yapcore.enums.BillStatus
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener

class MyBillsItemViewHolder(private val layoutItemMyBillsBinding: LayoutItemMyBillsBinding) :
    RecyclerView.ViewHolder(layoutItemMyBillsBinding.root) {

    fun onBind(
        billModel: ViewBillModel?,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        if (billModel?.status == BillStatus.OVERDUE.name) {
            val amountSpan =
                SpannableString("${billModel.settlementCurrency} ${billModel.totalAmountDue}")
            layoutItemMyBillsBinding.tvAmount.text = billModel.settlementCurrency?.length?.let {
                Utils.setSpan(
                    0,
                    it,
                    amountSpan,
                    ContextCompat.getColor(
                        layoutItemMyBillsBinding.tvAmount.context,
                        R.color.greyDark
                    )
                )
            }
        }

        layoutItemMyBillsBinding.viewModel =
            MyBillsItemViewModel(
                billModel,
                position,
                onItemClickListener
            )
        layoutItemMyBillsBinding.executePendingBindings()
    }
}
