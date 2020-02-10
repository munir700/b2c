package co.yap.modules.dashboard.more.yapforyou.adapters

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemYapForYouBinding
import co.yap.modules.dashboard.more.yapforyou.Achievement
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.helpers.Utils

class YAPForYouAdapter(context: Context, private val list: MutableList<Achievement>) :
    BaseBindingRecyclerAdapter<Achievement, RecyclerView.ViewHolder>(list) {

    private var dimensions: IntArray = Utils.getCardDimensions(context, 43, 45)

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_yap_for_you

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return YAPForYouItemViewHolder(binding as ItemYapForYouBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is YAPForYouItemViewHolder) {
            holder.onBind(position, list[position], dimensions, onItemClickListener)
        }
    }
}
