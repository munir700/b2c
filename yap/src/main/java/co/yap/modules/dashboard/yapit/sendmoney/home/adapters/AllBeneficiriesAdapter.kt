package co.yap.modules.dashboard.yapit.sendmoney.home.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.LayoutItemBeneficiaryBinding
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.interfaces.OnItemClickListener

class AllBeneficiriesAdapter(
    private val list: MutableList<Beneficiary>
) : BaseBindingRecyclerAdapter<Beneficiary, RecyclerView.ViewHolder>(list) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.layout_item_beneficiary

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return AllBeneficiariesItemViewHolder(binding as LayoutItemBeneficiaryBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is AllBeneficiariesItemViewHolder) {
            holder.onBind(list[position], position, onItemClickListener)
        }
    }

    class AllBeneficiariesItemViewHolder(
        private val itemContactsBinding: LayoutItemBeneficiaryBinding
    ) :
        RecyclerView.ViewHolder(itemContactsBinding.root) {

        fun onBind(
            beneficiary: Beneficiary?,
            position: Int,
            onItemClickListener: OnItemClickListener?
        ) {

            itemContactsBinding.viewModel = BeneficiaryItemViewModel(beneficiary, position, onItemClickListener)
            itemContactsBinding.executePendingBindings()

        }
    }

}
