package co.yap.modules.location.tax

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.yapcore.BaseBindingSearchRecylerAdapter
import co.yap.yapcore.R
import co.yap.yapcore.databinding.ItemTaxInfoBinding

class TaxInfoAdaptor(private val list: MutableList<TaxModel>) :
    BaseBindingSearchRecylerAdapter<TaxModel, RecyclerView.ViewHolder>(list) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_tax_info

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return TaxItemItemViewHolder(binding as ItemTaxInfoBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is TaxItemItemViewHolder) {
            holder.onBind(list[position], position, onItemClickListener)
        }
    }

    override fun filterItem(constraint: CharSequence?, item: TaxModel): Boolean {
//        val filterString = constraint.toString().toLowerCase()
//        val filterableString =
//            item.countries[0] ?: "" + "" + item.mobileNo ?: ""
//        val filterableStringForName = item.title ?: ""
//        return (filterableString.toLowerCase().contains(filterString) || filterableStringForName.toLowerCase().contains(
//            filterString
//        ))
        return false
    }
}
