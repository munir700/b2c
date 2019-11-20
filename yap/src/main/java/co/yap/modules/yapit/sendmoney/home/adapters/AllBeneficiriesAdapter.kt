package co.yap.modules.yapit.sendmoney.home.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemBeneficiariesBinding
import co.yap.modules.dashboard.yapit.y2y.home.phonecontacts.YapContactItemViewModel
import co.yap.networking.customers.requestdtos.Contact
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener

class AllBeneficiriesAdapter(private val list: MutableList<Contact>) :
    BaseBindingRecyclerAdapter<Contact, RecyclerView.ViewHolder>(list) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_beneficiaries

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return AllBeneficiariesItemViewHolder(binding as ItemBeneficiariesBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is AllBeneficiariesItemViewHolder) {
            holder.onBind(list[position], position, onItemClickListener)
        }
    }


    class AllBeneficiariesItemViewHolder(private val itemContactsBinding: ItemBeneficiariesBinding) :
        RecyclerView.ViewHolder(itemContactsBinding.root) {

        fun onBind(
            contact: Contact?,
            position: Int,
            onItemClickListener: OnItemClickListener?
        ) {

            itemContactsBinding.lyUserImage.tvNameInitials.background = Utils.getContactBackground(
                itemContactsBinding.lyUserImage.tvNameInitials.context,
                position
            )


            itemContactsBinding.lyUserImage.tvNameInitials.setTextColor(
                Utils.getContactColors(
                    itemContactsBinding.lyUserImage.tvNameInitials.context, position
                )
            )
            itemContactsBinding.viewModel =
                YapContactItemViewModel(contact, position, onItemClickListener)
            itemContactsBinding.executePendingBindings()
        }
    }

}
