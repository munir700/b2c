package co.yap.modules.location.kyc_additional_info.tax_info

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.R
import co.yap.yapcore.databinding.ItemTaxInfoBinding

class TaxInfoAdaptor(
    private val list: MutableList<TaxModel>,
    var amendmentMap: HashMap<String?, List<String>?>?,
    private val stateFromViewModel: ITaxInfo.State?,
    private val listener: ITaxItemOnClickListenerInterface? = null
) :
    BaseBindingRecyclerAdapter<TaxModel, RecyclerView.ViewHolder>(list) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_tax_info

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return TaxItemItemViewHolder(
            binding as ItemTaxInfoBinding,
            stateFromViewModel,
            listener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is TaxItemItemViewHolder) {
            holder.onBind(
                list[position],
                position,
                amendmentMap,
                onItemClickListener
            )
        }
    }
}
