package co.yap.yapcore.transactions

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.R
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.transactions.interfaces.LoadMoreListener

class TransactionRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private var rvTransaction: RecyclerView? = null
    private var clickListener: OnItemClickListener? = null
    private var onLoadMoreListener: LoadMoreListener? = null

    init {
        val view: View =
            inflate(context, R.layout.layout_transaction_recycler_view, null)
        view.layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        addView(view)
        rvTransaction = view.findViewById(R.id.rvTransactions)
        initAdapter()
        rvTransaction?.addOnScrollListener(
            object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager =
                        rvTransaction?.layoutManager as LinearLayoutManager
                    val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                    if (lastVisiblePosition == layoutManager.itemCount - 1) {
                        onLoadMoreListener?.onLoadMore()
                    }
                }
            })
    }

    private fun initAdapter() {
        rvTransaction?.layoutManager = LinearLayoutManager(context)
        rvTransaction?.adapter =
            TransactionsAdapter(mutableListOf(), object : OnItemClickListener {
                override fun onItemClick(view: View, data: Any, pos: Int) {
                    clickListener?.onItemClick(view, data, pos)
                }
            })
        getRecycleViewAdaptor()?.allowFullItemClickListener = true
    }

    fun addListToAdapter(list: MutableList<HomeTransactionListData>) {
        getRecycleViewAdaptor()?.addList(list)
    }

    fun setListToAdapter(list: MutableList<HomeTransactionListData>) {
        getRecycleViewAdaptor()?.setList(list)
    }

    fun setItemToAdapter() {
        val item: HomeTransactionListData? =
            getRecycleViewAdaptor()?.getDataForPosition(getRecycleViewAdaptor()?.itemCount ?: 0 - 1)
                ?.copy()
        item?.let {
            it.totalAmount = "loader"
            getRecycleViewAdaptor()?.run { addListItem(it) }
        }
    }

    fun setItemClickListener(clickListener: OnItemClickListener) {
        this.clickListener = clickListener
    }

    fun setLoadMoreListener(onLoadMoreListener: LoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener
    }

    fun getRecycleViewAdaptor(): TransactionsAdapter? {
        return if (rvTransaction?.adapter is TransactionsAdapter) {
            (rvTransaction?.adapter as TransactionsAdapter)
        } else {
            null
        }
    }
}