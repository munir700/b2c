package co.yap.widgets.expandablelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment
import co.yap.yapcore.R
import co.yap.yapcore.interfaces.OnItemClickListener
import net.cachapa.expandablelayout.ExpandableLayout

class PopParentAdapter(
    private val titles: ArrayList<String?>,
    private val purposeCategories: Map<String?, List<PurposeOfPayment>>?,
    private val recyclerView: RecyclerView,
    private val mListener: OnItemClickListener
) :
    RecyclerView.Adapter<PopParentAdapter.ViewHolder>() {
    private var selectedItem = UNSELECTED
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.parent_row, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return purposeCategories?.keys?.size ?: 0
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener {
        private val expandableLayout: ExpandableLayout =
            itemView.findViewById(R.id.expandable_layout)
        private val itemLayout: LinearLayout
        private val nestedRV: RecyclerView =
            itemView.findViewById(R.id.recycler_view_child)
        private val arrow: ImageView =
            itemView.findViewById(R.id.arrow)
        private val expandButton: TextView
        fun bind() {
            val position = adapterPosition
            val isSelected = position == selectedItem
            expandButton.text = titles[position]
            itemLayout.isSelected = isSelected
            expandableLayout.setExpanded(isSelected, false)
            val childList = (purposeCategories?.get(titles[position]))
            nestedRV.adapter = ExpandableChildAdapter(
                itemView.context,
                childList as ArrayList<PurposeOfPayment>, mListener
            )
        }

        override fun onClick(view: View) {
            val holder =
                recyclerView.findViewHolderForAdapterPosition(selectedItem) as ViewHolder?
            if (holder != null) {
                holder.itemLayout.isSelected = false
                holder.expandableLayout.collapse()
            }
            val position = adapterPosition
            selectedItem = if (position == selectedItem) {
                UNSELECTED
            } else {
                itemLayout.isSelected = true
                expandableLayout.expand()
                position
            }
        }

        override fun onExpansionUpdate(
            expansionFraction: Float,
            state: Int
        ) {
            if (state == ExpandableLayout.State.EXPANDING) {
                recyclerView.smoothScrollToPosition(adapterPosition)
            }
        }

        init {
            nestedRV.layoutManager = LinearLayoutManager(itemView.context)
            expandableLayout.setInterpolator(AccelerateInterpolator())
            expandableLayout.setOnExpansionUpdateListener(this)
            expandButton = itemView.findViewById(R.id.expand_button)
            itemLayout = itemView.findViewById(R.id.main)
            itemLayout.setOnClickListener(this)
            expandableLayout.setOnExpansionUpdateListener { _, _ ->
                if (expandableLayout.isExpanded) {
                    arrow.rotation = 180f
                } else {
                    arrow.rotation = 0f
                }
            }
        }
    }

    companion object {
        private const val UNSELECTED = -1
    }

}