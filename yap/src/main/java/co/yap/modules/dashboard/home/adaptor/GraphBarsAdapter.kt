package co.yap.modules.dashboard.home.adaptor

import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemBarChartBinding
import co.yap.modules.dashboard.home.ChartView
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.BaseBindingRecyclerAdapter


class GraphBarsAdapter(
    private val listItems: MutableList<HomeTransactionListData>,
    val viewModel: IYapHome.ViewModel
) : BaseBindingRecyclerAdapter<HomeTransactionListData, RecyclerView.ViewHolder>(listItems),
    View.OnFocusChangeListener {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_bar_chart

    override fun onCreateViewHolder(binding: ViewDataBinding): GraphViewHolder {
        return GraphViewHolder(binding as ItemBarChartBinding, viewModel)
    }

    companion object {
        var previouslySelected: Int = 0
        var isCellHighlighted: Boolean = false
        var isCellHighlightedFromTransaction: Boolean = false
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GraphViewHolder).onBind(listItems[position])
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        //if (!viewHolder.transactionBar.hasFocus()) {
        //   viewHolder.transactionBar.unSelectHighlightedBarOnGraphClick(hasFocus)
        //}
    }

    class GraphViewHolder(
        private val itemBarChartBinding: ItemBarChartBinding,
        private val viewModel: IYapHome.ViewModel
    ) :
        RecyclerView.ViewHolder(itemBarChartBinding.root) {

        fun onBind(transactionModel: HomeTransactionListData) {
            transactionModel.amountPercentage =
                calculatePercentagePerDayFromClosingBalance(transactionModel.closingBalance)

            val item =
                ChartView(
                    itemBarChartBinding.parent.context,
                    transactionModel.amountPercentage.toInt()
                )

            val params = LinearLayout.LayoutParams(
                26,
                transactionModel.amountPercentage.toInt()
            )
            params.marginStart = 5
            params.marginEnd = 5
            //holder.transactionBar.onFocusChangeListener = this
            itemBarChartBinding.parent.addView(item, params)
            Log.e(
                "graph1",
                "pos $adapterPosition graph_height -> " + transactionModel.amountPercentage
            )
//            itemBarChartBinding.root.setOnClickListener {
//                if (isCellHighlighted) {
//                    if (isCellHighlightedFromTransaction) {
//                        itemBarChartBinding.transactionBar.unSelectHighlightedBarOnTransactionCellClick(
//                            true
//                        )
//                    } else {
//                        itemBarChartBinding.transactionBar.unSelectHighlightedBarOnTransactionCellClick(
//                            false
//                        )
//                    }
//                } else {
//                    itemBarChartBinding.transactionBar.unSelectHighlightedBarOnGraphClick(
//                        isCellHighlighted
//                    )
//                }
//            }
            if (position == 0) {
                //itemBarChartBinding.transactionBar.OnBarItemTouchEvent()
            }
        }

//        fun calculatePercentagePerDayFromClosingBalance(closingBalance: Double): Double {
//            return (closingBalance / viewModel.MAX_CLOSING_BALANCE) * 100
//        }

        private fun calculatePercentagePerDayFromClosingBalance(closingBalance: Double): Double {
            closingBalance.toString().replace("-", "").toDouble()
            val percentage: Double = (closingBalance.toString().replace(
                "-",
                ""
            ).toDouble() / viewModel.MAX_CLOSING_BALANCE) * 100
            return percentage
        }

    }


}