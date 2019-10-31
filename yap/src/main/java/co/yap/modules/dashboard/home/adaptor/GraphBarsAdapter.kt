package co.yap.modules.dashboard.home.adaptor

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemBarChartBinding
import co.yap.modules.dashboard.home.ChartView
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.BaseBindingRecyclerAdapter
import kotlinx.android.synthetic.main.item_bar_chart.view.*


class GraphBarsAdapter(
    private val listItems: MutableList<HomeTransactionListData>,
//    private val listItems: MutableLiveData<List<HomeTransactionListData>>,
    /*val context: Context,*/
    val maxClosingBalance: Double
) : BaseBindingRecyclerAdapter<HomeTransactionListData, GraphBarsAdapter.ViewHolder>(listItems)/*,
    View.OnFocusChangeListener*/ {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_bar_chart

    override fun onCreateViewHolder(binding: ViewDataBinding): ViewHolder {
        return ViewHolder(binding as ItemBarChartBinding,maxClosingBalance)
    }


//    class TransactionsHeaderAdapter(private val list: MutableList<HomeTransactionListData>) :
//        BaseBindingRecyclerAdapter<HomeTransactionListData, GraphBarsAdapter.ViewHolder>(list) {


    lateinit var viewHolder: ViewHolder

    companion object {
        var previouslySelected: Int = 0
        var isCellHighlighted: Boolean = false
        var isCellHighlightedFromTransaction: Boolean = false
    }


//    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
//        val v = LayoutInflater.from(p0.context).inflate(R.layout.item_bar_chart, p0, false)
//        return ViewHolder(v)
//    }

    override fun getItemCount(): Int {
        return listItems!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolder).onBind(listItems[position])

//        viewHolder = holder
//        val transactionModel: HomeTransactionListData = listItems!![position]
//        transactionModel.amountPercentage =
//            calculatePercentagePerDayFromClosingBalance(transactionModel.closingBalance)
//        holder.transactionBar.onFocusChangeListener = this
//        holder.transactionBar.setBarHeight(transactionModel.amountPercentage)
//
//        holder.itemView.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                if (isCellHighlighted) {
//                    if (isCellHighlightedFromTransaction) {
//                        holder.transactionBar.unSelectHighlightedBarOnTransactionCellClick(true)
//                    } else {
//                        holder.transactionBar.unSelectHighlightedBarOnTransactionCellClick(false)
//                    }
//                } else {
//                    holder.transactionBar.unSelectHighlightedBarOnGraphClick(isCellHighlighted)
//                }
//            }
//
//        })
//        if (position == 0) {
//            holder.transactionBar.OnBarItemTouchEvent()
//        }
    }




//    override fun onFocusChange(v: View?, hasFocus: Boolean) {
//        if (!viewHolder.transactionBar.hasFocus()) {
//            viewHolder.transactionBar.unSelectHighlightedBarOnGraphClick(hasFocus)
//        }
//    }

//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val transactionBar: ChartView = itemView.transactionBar
//    }
//    class ViewHolder(itemBarChartBinding: ItemBarChartBinding) : RecyclerView.ViewHolder(ItemBarChartBinding.root) {
//
//        val transactionBar: ChartView = itemView.transactionBar
//    }


    class ViewHolder(
        private val itemBarChartBinding: ItemBarChartBinding,
        val maxClosingBalance: Double
    ) :
        RecyclerView.ViewHolder(itemBarChartBinding.root) {
        //
//        val transactionBar: ChartView = itemView.transactionBar

        fun onBind(transactionModel: HomeTransactionListData) {
//            itemBarChartBinding.transactionBar= homeTransaction.date

            val transactionBar: ChartView = itemView.transactionBar

//            viewHolder = holder
             transactionModel.amountPercentage =  calculatePercentagePerDayFromClosingBalance(transactionModel.closingBalance)
//           transactionBar.onFocusChangeListener = this
            transactionBar.setBarHeight(transactionModel.amountPercentage)

            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    if (isCellHighlighted) {
                        if (isCellHighlightedFromTransaction) {
                            transactionBar.unSelectHighlightedBarOnTransactionCellClick(true)
                        } else {
                            transactionBar.unSelectHighlightedBarOnTransactionCellClick(false)
                        }
                    } else {
                        transactionBar.unSelectHighlightedBarOnGraphClick(isCellHighlighted)
                    }
                }

            })
            if (position == 0) {
                transactionBar.OnBarItemTouchEvent()
            }

        }
        fun calculatePercentagePerDayFromClosingBalance(closingBalance: Double): Double {

            return (closingBalance / maxClosingBalance) * 100


        }

    }


}