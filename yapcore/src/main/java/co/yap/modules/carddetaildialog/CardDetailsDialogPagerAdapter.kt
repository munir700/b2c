package co.yap.modules.carddetaildialog

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.R
import co.yap.yapcore.databinding.DialogCardDetailsCardExpiryBinding
import co.yap.yapcore.databinding.DialogCardDetailsCardSerialNumberBinding

class CardDetailsDialogPagerAdapter(private val list: MutableList<CardDetailsModel>) :
    BaseBindingRecyclerAdapter<CardDetailsModel, RecyclerView.ViewHolder>(list) {
    private val serialNumber = 0
    private val expiryDetails = 1

    override fun getLayoutIdForViewType(viewType: Int): Int =
        if (viewType == serialNumber) R.layout.dialog_card_details_card_serial_number else R.layout.dialog_card_details_card_expiry

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return if (binding is DialogCardDetailsCardSerialNumberBinding) CardDetailsSerialNumberViewHolder(
            binding
        ) else CardDetailsExpiryViewHolder(
            binding as DialogCardDetailsCardExpiryBinding
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is CardDetailsSerialNumberViewHolder) {
            holder.onBind(list[position], position)
        } else if (holder is CardDetailsExpiryViewHolder) {
            holder.onBind(list[position], position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}