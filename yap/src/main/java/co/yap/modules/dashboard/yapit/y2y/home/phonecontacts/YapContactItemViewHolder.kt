package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemContactsBinding
import co.yap.networking.customers.requestdtos.Contact

class YapContactItemViewHolder(private val itemContactsBinding: ItemContactsBinding) :
    RecyclerView.ViewHolder(itemContactsBinding.root) {

    fun onBind(contact: Contact?) {
        itemContactsBinding.viewModel = YapContactItemViewModel(contact)
        itemContactsBinding.executePendingBindings()
    }
}