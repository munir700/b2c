package co.yap.modules.yapit.sendmoney.adapters

import android.content.Context
import androidx.databinding.ViewDataBinding
import co.yap.countryutils.country.Country
import co.yap.modules.yapit.sendmoney.adapters.CountryAdapter.ViewHolder
import co.yap.yapcore.BaseBindingArrayAdapter
import co.yap.yapcore.BaseBindingHolder


public class CountryAdapter(context: Context, resource: Int, objects: List<Country>) :
    BaseBindingArrayAdapter<Country, ViewHolder>(context, resource, objects) {

    override fun createViewHolder(binding: ViewDataBinding): ViewHolder {
        return ViewHolder(binding)
    }

//    class HeaderViewHolder(private val itemTransactionListHeaderBinding: ItemTransactionListHeaderBinding) :
//        RecyclerView.ViewHolder(itemTransactionListHeaderBinding.root) {
//
//        fun onBind(homeTransaction: HomeTransactionListData, adaptorClick: OnItemClickListener) {
//
//            //itemTransactionListHeaderBinding.tvTransactionDate.text = homeTransaction.date}
//        }
//    }

    inner class ViewHolder(binding: ViewDataBinding) : BaseBindingHolder(binding) {

        private fun onBind(obj: Any) {

        }
    }
}
