package co.yap.modules.dashboard.home.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.modules.dashboard.home.component.ChartViewV2
import co.yap.modules.dashboard.home.helpers.transaction.TransactionsViewHelper
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.widgets.tooltipview.TooltipView
import kotlinx.android.synthetic.main.item_bar_chart_v2.view.*


class GraphBarsAdapterV2(
    private val listItems: ArrayList<HomeTransactionListData>,
    val context: Context,private val tooltip: TooltipView? , private val helper: TransactionsViewHelper
) : RecyclerView.Adapter<GraphBarsAdapterV2.ViewHolder>(), View.OnFocusChangeListener {
    //lateinit var viewHolder: ViewHolder

    companion object {
        var previouslySelected: Int = -1
        var isCellHighlighted: Boolean = false
        var isCellHighlightedFromTransaction: Boolean = false
    }

    private var checkedPosition = 0

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.item_bar_chart_v2, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //viewHolder = holder
        val transactionModel: HomeTransactionListData = listItems[position]
        holder.transactionBar.onFocusChangeListener = this
        holder.transactionBar.setBarHeight(transactionModel.amountPercentage)

        if (checkedPosition == -1) {
            holder.transactionBar.isSelected =false

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
           holder.transactionBar.isSelected =true
           //v.postDelayed({},200)
           helper.addTooltip(v.findViewById(R.id.transactionBar),transactionModel)
           if (checkedPosition != holder.adapterPosition) {
               notifyItemChanged(checkedPosition);
               checkedPosition = holder.adapterPosition
           }
       }
//        if (position == 0) {
//            holder.transactionBar.OnBarItemTouchEvent()
//        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
//        if (!viewHolder.transactionBar.hasFocus()) {
//            viewHolder.transactionBar.unSelectHighlightedBarOnGraphClick(hasFocus)
//        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val transactionBar: ChartViewV2 = itemView.transactionBar
    }


}