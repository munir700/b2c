package co.yap.modules.dashboard.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.modules.dashboard.models.TransactionModel
import kotlinx.android.synthetic.main.item_bar_chart.view.*


class GraphBarsAdapter(val listItems: ArrayList<TransactionModel>, val context: Context) :
    RecyclerView.Adapter<GraphBarsAdapter.ViewHolder>(), View.OnFocusChangeListener {
    lateinit var viewHolder: ViewHolder

    companion object {
        var previouslySelected: Int = 0
        var isCellHighlighted: Boolean = false//graph
        var isCellHighlightedFromTransaction: Boolean = false//graph

//          var isBarHighLighted: Boolean = false
//              get() {
//                  return field
//              }
//              set(value) {
//
//              }

    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.item_bar_chart, p0, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        viewHolder = holder
        val transactionModel: TransactionModel = listItems.get(position)
        holder.transactionBar.onFocusChangeListener = this
        holder.transactionBar.setBarHeight(transactionModel.amountPercentage)

//
//        holder.itemView.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                if (isCellHighlighted){
////                    list
//                    holder.transactionBar.unSelectHighlightedBarOnGraphClick(isCellHighlighted)
//
//                }else{
//                    holder.transactionBar.unSelectHighlightedBarOnGraphClick(isCellHighlighted)
//
//                    // graph
//                }
//            }
//
//        })

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (isCellHighlighted){
//                    list
                    if (isCellHighlightedFromTransaction){
                        //creat
                        holder.transactionBar.unSelectHighlightedBarOnTransactionCellClick(true)

                    }else{
                        //fade out
                        holder.transactionBar.unSelectHighlightedBarOnTransactionCellClick(false)

                    }



                }else{
                    holder.transactionBar.unSelectHighlightedBarOnGraphClick(isCellHighlighted)

                    // graph
                }
            }

        })
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (!viewHolder.transactionBar.hasFocus()) {
            viewHolder.transactionBar.unSelectHighlightedBarOnGraphClick(hasFocus)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val transactionBar = itemView.transactionBar

    }
}