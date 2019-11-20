package co.yap.modules.dashboard.home.adaptor

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemBarChartV2Binding
import co.yap.modules.dashboard.home.component.ChartViewV2
import co.yap.modules.dashboard.home.helpers.transaction.TransactionsViewHelper
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.BaseBindingRecyclerAdapter
import kotlinx.android.synthetic.main.item_bar_chart_v2.view.*


class GraphBarsAdapter(
    private val listItems: MutableList<HomeTransactionListData>,
    val viewModel: IYapHome.ViewModel
) : BaseBindingRecyclerAdapter<HomeTransactionListData, GraphBarsAdapter.GraphViewHolder>(listItems),
    View.OnFocusChangeListener {

    private var checkedPosition = 0
    var helper: TransactionsViewHelper? = null

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_bar_chart_v2

    override fun onCreateViewHolder(binding: ViewDataBinding): GraphViewHolder {
        return GraphViewHolder(binding as ItemBarChartV2Binding, viewModel)
    }

    companion object {
        var previouslySelected: Int = 0
        var isCellHighlighted: Boolean = false
        var isCellHighlightedFromTransaction: Boolean = false
    }

    override fun onBindViewHolder(holder: GraphViewHolder, position: Int) {
        val transactionModel: HomeTransactionListData = listItems[position]
        holder.transactionBar.onFocusChangeListener = this
        holder.onBind(transactionModel)

        if (checkedPosition == -1) {
            holder.transactionBar.isSelected = false

        } else {
            holder.transactionBar.isSelected = checkedPosition == holder.adapterPosition
        }

        holder.itemView.setOnClickListener { v ->
            ////                if (isCellHighlighted) {
            ////                    if (isCellHighlightedFromTransaction) {
            ////
            ////                    holder.transactionBar.unSelectHighlightedBarOnTransactionCellClick(true)
            ////                  //  holder.transactionBar.fadeOutAnimation()
            ////
            ////                    } else {
            ////                       holder.transactionBar.unSelectHighlightedBarOnTransactionCellClick(false)
            ////                    }
            ////               } else {
            ////                    holder.transactionBar.unSelectHighlightedBarOnGraphClick(isCellHighlighted)
            ////                }
            //

            //v.postDelayed({},200)
            if (checkedPosition != holder.adapterPosition) {
                holder.transactionBar.isSelected = true


//                v.findViewById<ChartViewV2>(R.id.transactionBar).let {
//                    val viewPosition = IntArray(2)
//                    it.getLocationInWindow(viewPosition)
//                    val screen = DisplayMetrics()
//                    (it.context as Activity).windowManager.defaultDisplay.getMetrics(screen)
//                    val t = getProfileBalloon(
//                        it.context,
//                        (((viewPosition[0]).toFloat()) / screen.widthPixels)
//                    )
//
//                    previousBalloon?.dismiss()
//                    previousBalloon = t
//                    // t.getContentView().parent.parent.parent
//                    t.showAlignTop(it, 0, -20)
//                }
//            val viewPosition = IntArray(2)
//            v.findViewById<ChartViewV2>(R.id.transactionBar).getLocationInWindow(viewPosition)
//            val screen = DisplayMetrics()
//            (v.context as Activity).windowManager.defaultDisplay.getMetrics(screen)
//            val t = getProfileBalloon(v.context, ((viewPosition[0].toFloat()+32) / screen.widthPixels))
//            t.getContentView().elevation = 20f
//            t.showAlignTop(v.findViewById<ChartViewV2>(R.id.transactionBar))


                helper?.addTooltip(v.findViewById(R.id.transactionBar), transactionModel)


                notifyItemChanged(checkedPosition)
                checkedPosition = holder.adapterPosition
            }
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        //if (!viewHolder.transactionBar.hasFocus()) {
        //   viewHolder.transactionBar.unSelectHighlightedBarOnGraphClick(hasFocus)
        //}
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

//    fun getProfileBalloon(baseContext: Context, arrowPosition: Float): Balloon {
//        return Balloon.Builder(baseContext)
//            .setArrowPosition(arrowPosition)
//            .setWidthRatio(0.40f)
//            .setCornerRadius(4f).setBackgroundColorResource(R.color.lp_light_blue)
//            .setLayout(R.layout.layout_tooltip_box)
//            .setDismissWhenTouchOutside(true)
//            .setBalloonAnimation(BalloonAnimation.FADE)
//
//            .build()
//    }

    class GraphViewHolder(
        itemBarChartBinding: ItemBarChartV2Binding,
        private val viewModel: IYapHome.ViewModel
    ) : RecyclerView.ViewHolder(itemBarChartBinding.root) {
        val transactionBar: ChartViewV2 = itemView.transactionBar

        fun onBind(transactionModel: HomeTransactionListData) {

            //if (!transactionBar.isBarValueSet) {
            transactionModel.amountPercentage =
                calculatePercentagePerDayFromClosingBalance(transactionModel.closingBalance)

            transactionBar.barHeight = transactionModel.amountPercentage.toFloat()
//            var params = transactionBar.layoutParams
//
//            params.height =itemView.context.dip2px(100) //100//transactionBar.barHeight.toInt()
//            transactionBar.layoutParams = params

//            transactionBar.requestLayout()
            // }
//            transactionBar.setBarHeight(transactionModel.amountPercentage)


//            val item =
//                ChartView(
//                    itemBarChartBinding.parent.context,
//                    transactionModel.amountPercentage.toInt()
//                )
//
//            item.id = position
//            val params = LinearLayout.LayoutParams(
//                26,
//                transactionModel.amountPercentage.toInt()
//            )
//            params.marginStart = 5
//            params.marginEnd = 5
//            //holder.transactionBar.onFocusChangeListener = this
//            itemBarChartBinding.root.setOnClickListener {
//                if (isCellHighlighted) {
//                    if (isCellHighlightedFromTransaction) {
//                        item.unSelectHighlightedBarOnTransactionCellClick(
//                            true
//                        )
//                    } else {
//                        item.unSelectHighlightedBarOnTransactionCellClick(
//                            false
//                        )
//                    }
//                } else {
//                    item.unSelectHighlightedBarOnGraphClick(
//                        isCellHighlighted
//                    )
//                }
//            }
//            itemBarChartBinding.parent.addView(item, params)
//            if (position == 0) {
//                item.OnBarItemTouchEvent()
//            }
        }

//        fun calculatePercentagePerDayFromClosingBalance(closingBalance: Double): Double {
//            return (closingBalance / viewModel.MAX_CLOSING_BALANCE) * 100
//        }

        private fun calculatePercentagePerDayFromClosingBalance(closingBalance: Double): Double {
            closingBalance.toString().replace("-", "").toDouble()
            val percentage: Double = (closingBalance.toString().replace(
                "-",
                ""
            ).toDouble() / viewModel.MAX_CLOSING_BALANCE)
            return percentage
        }

    }


}