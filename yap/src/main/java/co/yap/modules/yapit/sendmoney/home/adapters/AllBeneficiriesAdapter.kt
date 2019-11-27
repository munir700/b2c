package co.yap.modules.yapit.sendmoney.home.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemBeneficiariesBinding
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener
 
class AllBeneficiriesAdapter(private val list: MutableList<Beneficiary>) :
    BaseBindingRecyclerAdapter<Beneficiary, RecyclerView.ViewHolder>(list) {

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
            contact: Beneficiary?,
            position: Int,
            onItemClickListener: OnItemClickListener?
        ) {

            itemContactsBinding.tvNameInitials.background = Utils.getContactBackground(
                itemContactsBinding.tvNameInitials.context,
                position
            )


            itemContactsBinding.tvNameInitials.setTextColor(
                Utils.getContactColors(
                    itemContactsBinding.tvNameInitials.context, position
                )
            )
            itemContactsBinding.viewModel =
                BeneficiaryItemViewModel(contact, position, onItemClickListener)
            itemContactsBinding.executePendingBindings()
        }
    }

}
