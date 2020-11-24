package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual

import androidx.databinding.ViewDataBinding
import co.yap.databinding.ItemVirtualCardBinding
import co.yap.modules.dashboard.cards.addpaymentcard.models.VirtualCardModel
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.interfaces.OnItemClickListener

class AddVirtualCardAdapter(
    private val list: MutableList<VirtualCardModel>) :
    BaseBindingRecyclerAdapter<VirtualCardModel, VirtualCardViewHolder>(list) {
    override fun onCreateViewHolder(binding: ViewDataBinding): VirtualCardViewHolder {
        return VirtualCardViewHolder(binding as ItemVirtualCardBinding)
    }

    override fun onBindViewHolder(holder: VirtualCardViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.onBind(position, list[position]
        )
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = getViewModel(viewType).layoutRes()

    fun getViewModel(viewType: Int) = VirtualCardItemViewModel()

}