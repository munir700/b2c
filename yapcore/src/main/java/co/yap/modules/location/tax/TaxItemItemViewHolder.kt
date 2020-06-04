package co.yap.modules.location.tax

import androidx.recyclerview.widget.RecyclerView
import co.yap.yapcore.databinding.ItemTaxInfoBinding
import co.yap.yapcore.interfaces.OnItemClickListener

class TaxItemItemViewHolder(private val itemTaxInfoBinding: ItemTaxInfoBinding) :
    RecyclerView.ViewHolder(itemTaxInfoBinding.root) {

    fun onBind(
        taxModel: TaxModel?,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        itemTaxInfoBinding.viewModel =
            TaxInfoItemViewModel(taxModel, position, onItemClickListener)
        itemTaxInfoBinding.executePendingBindings()
    }
}