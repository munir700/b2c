package co.yap.modules.dashboard.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.modules.dashboard.HorizontalProgressView
import co.yap.widgets.graph.data.DataPoint
import co.yap.widgets.graph.extensions.toDataPoints

class DashboardAdapter(val listItems: HashMap<String, Float>, val context: Context) :
    RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.item_bar_chart, p0, false)
        return ViewHolder(v);
    }
    override fun getItemCount(): Int {
        return listItems.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items:HashMap<String, Float> = listItems
        val item1 : LinkedHashMap<String, Float> = LinkedHashMap<String, Float>()
        for (abc in items ) {
            item1.put(abc.key, abc.value)

        }
//        HorizontalProgressView.setGradientColors(intArrayOf(Color.RED, Color.BLUE))

            var data: List<DataPoint> = item1.toDataPoints()

        holder?.transactionBar.animation.duration = 1000
        holder?.transactionBar.animate(data.get(position))
//        holder?.transactionBar.animate(item1)
//        holder?.tvCheck.text=item1.get(position).toString()
        holder?.tvCheck.text=listItems.keys.size.toString()


//        p0.transactionBar?.animation!!.duration = 1000
//        p0.transactionBar?.animate(item1)


//        p0.transactionBar? = listItems[p1].name
     }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val transactionBar = itemView.findViewById<co.yap.widgets.graph.view.BarChartView>(R.id.transactionBar)
        val tvCheck = itemView.findViewById<TextView>(R.id.tvCheck)

    }
}