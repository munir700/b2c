package co.yap.yapcore.transactions

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.yap.yapcore.R
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.transactions.interfaces.LoadMoreListener

class TransactionRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    var rvTransaction: RecyclerView? = null
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
        setScrollCallback()
    }

    private fun setScrollCallback() {
        rvTransaction?.addOnScrollListener(
            object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager =
                        rvTransaction?.layoutManager as LinearLayoutManager
                    val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                    if (lastVisiblePosition == layoutManager.itemCount - 1 &&
                        false == (rvTransaction?.adapter as? TransactionsAdapter)?.isLoaderMore?.value
                    ) {
                        (rvTransaction?.adapter as? TransactionsAdapter)?.isLoaderMore?.value =
                            true
                        onLoadMoreListener?.onLoadMore()
                        (rvTransaction?.adapter as? TransactionsAdapter)?.itemCount?.let {
                            (rvTransaction?.adapter as? TransactionsAdapter)?.notifyItemInserted(
                                it
                            )
                        }
                    }

                }
            })
    }

    private fun initAdapter() {
        rvTransaction?.layoutManager = LinearLayoutManager(context)
        rvTransaction?.adapter =
            TransactionsAdapter(mutableListOf(), clickListener)
        (rvTransaction?.adapter as? TransactionsAdapter)?.allowFullItemClickListener = true
    }

    fun setItemClickListener(clickListener: OnItemClickListener) {
        this.clickListener = clickListener
    }

    fun setLoadMoreListener(onLoadMoreListener: LoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener
    }
}