package co.yap.modules.dashboard.yapit.sendmoney.landing

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.modules.dashboard.yapit.addmoney.landing.AddMoneyLandingOptions
import co.yap.yapcore.BaseBindingRecyclerAdapter

class SendMoneyLandingAdapter(
    context: Context,
    private val list: MutableList<AddMoneyLandingOptions>
) :
    BaseBindingRecyclerAdapter<AddMoneyLandingOptions, RecyclerView.ViewHolder>(list) {
    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getLayoutIdForViewType(viewType: Int): Int {
        TODO("Not yet implemented")
    }
}