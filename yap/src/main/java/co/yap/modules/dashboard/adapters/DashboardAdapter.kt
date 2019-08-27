package co.yap.modules.dashboard.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.modules.onboarding.models.TransactionModel
import kotlinx.android.synthetic.main.item_bar_chart.view.*

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

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val transactionBar = itemView.transactionBar
//        val transactionBar = itemView.findViewById<co.yap.modules.dashboard.ChartView>(R.id.transactionBar)

    }
}