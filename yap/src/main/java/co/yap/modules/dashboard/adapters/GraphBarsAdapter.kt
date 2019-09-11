package co.yap.modules.dashboard.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.modules.dashboard.ChartView
import co.yap.modules.dashboard.models.TransactionModel
import it.sephiroth.android.library.xtooltip.Tooltip
import kotlinx.android.synthetic.main.item_bar_chart.view.*


class GraphBarsAdapter(
    private val listItems: ArrayList<TransactionModel>,
    val context: Context
) :
    RecyclerView.Adapter<GraphBarsAdapter.ViewHolder>(), View.OnFocusChangeListener {
    lateinit var viewHolder: ViewHolder

    companion object {
        var previouslySelected: Int = 0
        var isCellHighlighted: Boolean = false
        var isCellHighlightedFromTransaction: Boolean = false
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.item_bar_chart, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        viewHolder = holder
        val transactionModel: TransactionModel = listItems[position]
        holder.transactionBar.onFocusChangeListener = this
        holder.transactionBar.setBarHeight(transactionModel.amountPercentage)

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (isCellHighlighted) {
                    if (isCellHighlightedFromTransaction) {
                        holder.transactionBar.unSelectHighlightedBarOnTransactionCellClick(true)
                    } else {
                        holder.transactionBar.unSelectHighlightedBarOnTransactionCellClick(false)
                    }
                } else {
                    holder.transactionBar.unSelectHighlightedBarOnGraphClick(isCellHighlighted)
                }
            }

        })
        if (position == 0) {
            holder.transactionBar.OnBarItemTouchEvent()
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (!viewHolder.transactionBar.hasFocus()) {
            viewHolder.transactionBar.unSelectHighlightedBarOnGraphClick(hasFocus)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val transactionBar: ChartView = itemView.transactionBar
    }
}