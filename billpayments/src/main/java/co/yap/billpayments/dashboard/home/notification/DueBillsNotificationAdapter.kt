package co.yap.billpayments.dashboard.home.notification

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.R
import co.yap.billpayments.databinding.ItemDueBillNotificationBinding
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.helpers.ImageBinding
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.managers.SessionManager

class DueBillsNotificationAdapter(
    private val context: Context,
    private val listItems: MutableList<ViewBillModel>
) :
    BaseBindingRecyclerAdapter<ViewBillModel, DueBillsNotificationAdapter.ViewHolder>(listItems) {
    private var dimensions: IntArray = Utils.getCardDimensions(context, 80, 15)

    override fun onCreateViewHolder(binding: ViewDataBinding): ViewHolder {
        return ViewHolder(binding as ItemDueBillNotificationBinding)
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_due_bill_notification

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(listItems[position])
    }

    inner class ViewHolder(val binding: ItemDueBillNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(dueBill: ViewBillModel) {
            val params = binding.cvNotification.layoutParams as RecyclerView.LayoutParams
            params.width = dimensions[0]
            binding.cvNotification.layoutParams = params
            binding.ivCross.visibility = View.VISIBLE
            binding.tvTitle.text = dueBill.billerInfo?.billerName
            ImageBinding.loadAvatar(
                binding.ivNotification,
                fullName = dueBill.billerInfo?.billerName,
                imageUrl = dueBill.billerInfo?.logo,
                position = adapterPosition
            )
            binding.tvDescription.text = "You have a ${dueBill.totalAmountDue.toFormattedCurrency(
                showCurrency = true,
                currency = SessionManager.getDefaultCurrency()
            )} outstanding on your ${dueBill.billerInfo?.billerName} bill."

            binding.cvNotification.setOnClickListener {
                onItemClickListener?.onItemClick(binding.cvNotification, dueBill, adapterPosition)
            }

            binding.ivCross.setOnClickListener {
                onItemClickListener?.onItemClick(binding.ivCross, dueBill, adapterPosition)
            }
        }
    }
}