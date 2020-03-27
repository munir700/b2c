package co.yap.sendMoney.fundtransfer.adaptors

import android.view.ViewGroup
import android.widget.TextView
import co.yap.sendmoney.R
import co.yap.widgets.expandableview.OmegaExpandableRecyclerView

class PurposeListAdapter: OmegaExpandableRecyclerView.Adapter<QuoteGlobalInfo, Quote>() {
    override fun provideGroupViewHolder(viewGroup: ViewGroup): GroupViewHolder {
        return ExGroupViewHolder(viewGroup)
    }

    override fun provideChildViewHolder(viewGroup: ViewGroup): ChildViewHolder {
    }

     open class ExGroupViewHolder(parent: ViewGroup?) : GroupViewHolder(parent,77) {


        private val textView: TextView
        protected fun onExpand(
            viewHolder: GroupViewHolder?,
            groupIndex: Int
        ) { // nothing
        }

        protected fun onCollapse(
            viewHolder: GroupViewHolder?,
            groupIndex: Int
        ) { // nothing
        }

        protected fun onBind(item: QuoteGlobalInfo) {
            textView.setText(item.getTitle())
        }

        init {
            textView = findViewById(R.id.textview_group_name)
        }
    }

}