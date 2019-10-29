package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts

import android.graphics.Color
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemContactsBinding
import co.yap.networking.customers.requestdtos.Contact
import co.yap.yapcore.interfaces.OnItemClickListener


class YapContactItemViewHolder(private val itemContactsBinding: ItemContactsBinding) :
    RecyclerView.ViewHolder(itemContactsBinding.root) {

    fun onBind(
        colors: ArrayList<String>,
        contact: Contact?,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {

        val unwrappedDrawable = AppCompatResources.getDrawable(
            itemContactsBinding.lyUserImage.tvNameInitials.context,
            R.drawable.bg_round_purple
        )
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor(colors[position % colors.size]))

        itemContactsBinding.lyUserImage.tvNameInitials.background = wrappedDrawable

        itemContactsBinding.viewModel =
            YapContactItemViewModel(contact, position, onItemClickListener)
        itemContactsBinding.executePendingBindings()
    }
}