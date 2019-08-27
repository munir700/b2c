package co.yap.modules.dashboard.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.modules.onboarding.models.TransactionModel
import kotlinx.android.synthetic.main.item_bar_chart.view.*
 import android.widget.LinearLayout


class DashboardAdapter(val listItems: ArrayList<TransactionModel>, val context: Context) :
    RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.item_bar_chart, p0, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transactionModel : TransactionModel = listItems.get(position)

        holder.transactionBar.setBarHeight(transactionModel.amountPercentage)
//        holder.transactionBarView.minimumHeight= transactionModel.amountPercentage.toInt()

        holder.tv.text= transactionModel.amountPercentage.toString()
//        holder.transactionBarView.setLayoutParams(ViewGroup.LayoutParams(15, transactionModel.amountPercentage.toInt()))

//         holder.transactionBarView.setHeight(transactionModel.amountPercentage.toInt())

        holder.transactionBarView.setLayoutParams(LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, (transactionModel.amountPercentage*10).toInt()))
//        holder.transactionBarView.setLayoutParams(LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, context.resources.getDimension(R.dimen._10sdp).toInt()))

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val transactionBar = itemView.transactionBar
        val transactionBarView = itemView.transactionBarView
        val tv = itemView.tv
//        val transactionBar = itemView.findViewById<co.yap.modules.dashboard.ChartView>(R.id.transactionBar)

    }
}