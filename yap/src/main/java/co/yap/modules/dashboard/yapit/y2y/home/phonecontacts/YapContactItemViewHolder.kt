package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemContactsBinding
import co.yap.networking.transactions.responsedtos.Contact

class YapContactItemViewHolder(private val itemYapStoreBinding: ItemContactsBinding) :
    RecyclerView.ViewHolder(itemYapStoreBinding.root) {

    fun onBind(contact: Contact?) {
        itemYapStoreBinding.viewModel = YapContactItemViewModel(contact)
        itemYapStoreBinding.executePendingBindings()
    }
}