package co.yap.modules.dashboard.yapit.addmoney

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemYapItAddMoneyBinding
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.helpers.Utils

class AddMoneyAdapter (context: Context, private val list: MutableList<AddMoneyOptions>) :
    BaseBindingRecyclerAdapter<AddMoneyOptions, RecyclerView.ViewHolder>(list) {

    private var dimensions: IntArray = Utils.getCardDimensions(context, 43, 45)

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_yap_it_add_money

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return AddMoneyItemViewHolder(binding as ItemYapItAddMoneyBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is AddMoneyItemViewHolder) {
            holder.onBind(position, list[position], dimensions, onItemClickListener)
        }
    }

}