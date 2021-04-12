package co.yap.billpayments.dashboard.home.notification

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.R
import co.yap.billpayments.databinding.ItemDueBillNotificationBinding
import co.yap.billpayments.dashboard.home.adapter.DueBill
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.helpers.ImageBinding
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.toFormattedCurrency

class DueBillsNotificationAdapter(
    private val context: Context,
    private val listItems: MutableList<DueBill>
) :
    BaseBindingRecyclerAdapter<DueBill, DueBillsNotificationAdapter.ViewHolder>(listItems) {
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
        fun onBind(dueBill: DueBill) {
            val params = binding.cvNotification.layoutParams as RecyclerView.LayoutParams
            params.width = dimensions[0]
            binding.cvNotification.layoutParams = params
            binding.ivCross.visibility = View.VISIBLE
            binding.tvTitle.text = dueBill.billerName
            ImageBinding.loadAvatar(
                binding.ivNotification,
                fullName = dueBill.billerName,
                imageUrl = dueBill.logoUrl,
                position = adapterPosition
            )
            binding.tvDescription.text = "You have a ${dueBill.amount.toFormattedCurrency(
                showCurrency = true,
                currency = dueBill.currency
            )} on your ${dueBill.billerName} bill."

            binding.cvNotification.setOnClickListener {
                onItemClickListener?.onItemClick(binding.cvNotification, dueBill, adapterPosition)
            }

            binding.ivCross.setOnClickListener {
                onItemClickListener?.onItemClick(binding.ivCross, dueBill, adapterPosition)
            }
        }
    }
}