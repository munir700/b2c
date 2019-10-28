package co.yap.modules.dashboard.home.adaptor

import android.content.Context
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemTransactionListHeaderBinding
import co.yap.modules.dashboard.home.helpers.transaction.ItemHeaderTransactionsViewModel
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.BasePagingBindingRecyclerAdapter
import co.yap.yapcore.databinding.ItemListFooterBinding
import kotlinx.android.synthetic.main.item_transaction_list_header.view.*


class TransactionsHeaderAdapter(val context: Context, retry: () -> Unit) :
    BasePagingBindingRecyclerAdapter<HomeTransactionListData>(retry, diffCallback) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_transaction_list_header

    override fun getLayoutIdForFooterType(viewType: Int): Int = R.layout.item_list_footer

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == contentView)
            (holder as HeaderViewHolder).onBind(getItem(position),context)

        else (holder as ListFooterViewHolder).onBind(getState())
    }

    override fun onCreateContentViewHolder(binding: ViewDataBinding): HeaderViewHolder {
        return HeaderViewHolder(binding as ItemTransactionListHeaderBinding)
    }

    override fun onCreateFooterViewHolder(binding: ViewDataBinding): ListFooterViewHolder {
        return ListFooterViewHolder(binding as ItemListFooterBinding)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<HomeTransactionListData>() {
            override fun areItemsTheSame(
                oldItem: HomeTransactionListData,
                newItem: HomeTransactionListData
            ): Boolean =
//              oldItem.id == newItem.id
                oldItem.date == newItem.date

            override fun areContentsTheSame(
                oldItem: HomeTransactionListData,
                newItem: HomeTransactionListData
            ): Boolean = oldItem == newItem
        }
    }


    class HeaderViewHolder(val itemTransactionListHeaderBinding: ItemTransactionListHeaderBinding) :
        RecyclerView.ViewHolder(itemTransactionListHeaderBinding.root) {
        val tvTransactionDate: TextView? = itemView.tvTransactionDate
        val tvTotalAmount: TextView? = itemView.tvTotalAmount
        var horizontalView: RecyclerView = itemView.rv_expanded_transactions_listing


        fun onBind(store: HomeTransactionListData? ,  context: Context) {
            itemTransactionListHeaderBinding.viewModel = ItemHeaderTransactionsViewModel(store)
            itemTransactionListHeaderBinding.executePendingBindings()

         tvTransactionDate!!.text=store!!.date
            tvTotalAmount!!.text=store!!.totalAmount

            //

            horizontalView.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL, false
            )

            val snapHelper = PagerSnapHelper()

           horizontalView.onFlingListener = null

            snapHelper.attachToRecyclerView(horizontalView)

           horizontalView.adapter =
                TransactionsListingAdapter(context!!, store.content as ArrayList<Content>)
        }





            //

        }
    }
//}