package co.yap.modules.dashboard.yapit.recent_transfers

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemCoreRecentBeneficiaryBinding
import co.yap.networking.customers.responsedtos.sendmoney.CoreRecentBeneficiaryItem
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.interfaces.OnItemClickListener

class CoreRecentTransferAdapter(
    context: Context,
    private val list: MutableList<CoreRecentBeneficiaryItem>
) :
    BaseBindingRecyclerAdapter<CoreRecentBeneficiaryItem, RecyclerView.ViewHolder>(list) {
    override fun getLayoutIdForViewType(viewType: Int): Int =
        R.layout.item_core_recent_beneficiary

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return ViewHolder(
            binding as ItemCoreRecentBeneficiaryBinding
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is ViewHolder) {
            holder.onBind(list[position], position, onItemClickListener)
        }
    }

    class ViewHolder(private val itemCoreRecentBeneficiaryBinding: ItemCoreRecentBeneficiaryBinding) :
        RecyclerView.ViewHolder(itemCoreRecentBeneficiaryBinding.root) {
        fun onBind(
            coreRecentBeneficiary: CoreRecentBeneficiaryItem,
            position: Int,
            onItemClickListener: OnItemClickListener?
        ) {
            itemCoreRecentBeneficiaryBinding.viewModel =
                CoreRecentBeneficiaryItemViewModel(
                    coreRecentBeneficiary,
                    position,
                    onItemClickListener
                )
            itemCoreRecentBeneficiaryBinding.executePendingBindings()
        }
    }
}