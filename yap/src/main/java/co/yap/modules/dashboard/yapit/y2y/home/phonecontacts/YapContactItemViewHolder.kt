package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemContactsBinding
import co.yap.networking.customers.requestdtos.Contact
import co.yap.yapcore.interfaces.OnItemClickListener


class YapContactItemViewHolder(private val itemContactsBinding: ItemContactsBinding) :
    RecyclerView.ViewHolder(itemContactsBinding.root) {

    fun onBind(
        contact: Contact?,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {

        val backgrounds = intArrayOf(
            R.drawable.bg_round_light_red,
            R.drawable.bg_round_light_blue,
            R.drawable.bg_round_light_green,
            R.drawable.bg_round_light_orange
        )

        val contactColors = intArrayOf(
            R.color.colorSecondaryMagenta,
            R.color.colorSecondaryBlue,
            R.color.colorSecondaryGreen,
            R.color.colorSecondaryOrange
        )

        itemContactsBinding.lyUserImage.tvNameInitials.background = ContextCompat.getDrawable(
            itemContactsBinding.lyUserImage.tvNameInitials.context,
            backgrounds[position % backgrounds.size]
        )
        try {
            itemContactsBinding.lyUserImage.tvNameInitials.setTextColor(
                ContextCompat.getColor(
                    itemContactsBinding.lyUserImage.tvNameInitials.context,
                    contactColors[position % contactColors.size]
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        itemContactsBinding.viewModel =
            YapContactItemViewModel(contact, position, onItemClickListener)
        itemContactsBinding.executePendingBindings()
    }
}