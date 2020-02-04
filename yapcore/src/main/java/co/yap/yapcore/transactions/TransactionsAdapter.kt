package co.yap.yapcore.transactions

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.BaseBindingSearchRecylerAdapter
import co.yap.yapcore.R
import co.yap.yapcore.databinding.ItemTransactionHeaderBinding
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.transactions.viewholders.HeaderViewHolder

class TransactionsAdapter(
    private val list: MutableList<HomeTransactionListData>,
    private val clickListener: OnItemClickListener
) : BaseBindingSearchRecylerAdapter<HomeTransactionListData, RecyclerView.ViewHolder>(list) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_transaction_header

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return HeaderViewHolder(binding as ItemTransactionHeaderBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is HeaderViewHolder) {
            holder.onBind(list[position], clickListener)
        }
    }


    override fun filterItem(constraint: CharSequence?, item: HomeTransactionListData): Boolean {
      return true
    }

}