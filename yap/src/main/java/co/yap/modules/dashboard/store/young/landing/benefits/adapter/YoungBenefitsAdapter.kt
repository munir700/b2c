package co.yap.modules.dashboard.store.young.landing.benefits.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import kotlinx.android.synthetic.main.item_benefits.view.*

class YoungBenefitsAdapter(
    private val listItems: ArrayList<YoungBenefitsModel>
) : RecyclerView.Adapter<YoungBenefitsAdapter.ViewHolder>() {
    lateinit var viewHolder: ViewHolder


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_benefits, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        viewHolder = holder
        val benefitsModel: YoungBenefitsModel = listItems[position]
        holder.tvBenefits.text = benefitsModel.benefits

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvBenefits: TextView = itemView.tvBenefit
    }

    interface OnItemClickedListener {
        fun onItemClick(benefitsModel: YoungBenefitsModel)
    }
}
