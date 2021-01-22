package co.yap.sendmoney.home.adapters

import androidx.databinding.ObservableField
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.networking.customers.responsedtos.sendmoney.IBeneficiary
import co.yap.sendmoney.R
import co.yap.sendmoney.databinding.LayoutItemBeneficiaryBinding
import co.yap.yapcore.BaseBindingSearchRecylerAdapter
import co.yap.yapcore.interfaces.OnItemClickListener

class AllBeneficiariesAdapter(
    private val list: MutableList<IBeneficiary>, val isSearching: ObservableField<Boolean> = ObservableField(false)
) : BaseBindingSearchRecylerAdapter<IBeneficiary, AllBeneficiariesAdapter.AllBeneficiariesItemViewHolder>(
    list
) {
    var sendMoneyType: String?=null
    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.layout_item_beneficiary

    override fun onCreateViewHolder(binding: ViewDataBinding): AllBeneficiariesItemViewHolder {
        return AllBeneficiariesItemViewHolder(binding as LayoutItemBeneficiaryBinding)
    }

    override fun onBindViewHolder(holder: AllBeneficiariesItemViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.onBind(list[position], position, onItemClickListener, sendMoneyType,isSearching)
    }

    class AllBeneficiariesItemViewHolder(
        private val itemContactsBinding: LayoutItemBeneficiaryBinding
    ) :
        RecyclerView.ViewHolder(itemContactsBinding.root) {

        fun onBind(
            beneficiary: IBeneficiary?,
            position: Int,
            onItemClickListener: OnItemClickListener?,
            sendMoneyType: String? = "",isSearching: ObservableField<Boolean>
        ) {
            itemContactsBinding.viewModel =
                BeneficiaryItemViewModel(beneficiary, position, sendMoneyType, onItemClickListener,isSearching)
            itemContactsBinding.executePendingBindings()
        }
    }

    override fun filterItem(constraint: CharSequence?, item: IBeneficiary): Boolean {
        val filterString = constraint.toString().toLowerCase()
        val nickname = item.subtitle?.toLowerCase() ?: ""
        val fullName = item.fullName?.toLowerCase()?:""

        return nickname.contains(filterString) || fullName.contains(filterString)
    }
}
