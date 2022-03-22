package co.yap.modules.dashboard.yapit.sendmoney.landing

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemYapItSendMoneyLandingBinding
import co.yap.modules.dashboard.yapit.sendmoney.landing.viewmodels.SendMoneyDashboardItemViewModel
import co.yap.modules.dashboard.yapit.sendmoney.main.SendMoneyOptions
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.interfaces.OnItemClickListener

class SendMoneyDashboardAdapter(
    context: Context,
    private val list: MutableList<SendMoneyOptions>
) :
    BaseBindingRecyclerAdapter<SendMoneyOptions, RecyclerView.ViewHolder>(list) {

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return ViewHolder(
            binding as ItemYapItSendMoneyLandingBinding
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is ViewHolder) holder.onBind(
            list[position],
            position,
            onItemClickListener
        )
    }

    override fun getLayoutIdForViewType(viewType: Int): Int =
        R.layout.item_yap_it_send_money_landing

    class ViewHolder(private val itemYapItSendMoneyBinding: ItemYapItSendMoneyLandingBinding) :
        RecyclerView.ViewHolder(itemYapItSendMoneyBinding.root) {
        fun onBind(
            addMoneyOptions: SendMoneyOptions,
            position: Int,
            onItemClickListener: OnItemClickListener?
        ) {
            itemYapItSendMoneyBinding.viewModel =
                SendMoneyDashboardItemViewModel(
                    addMoneyOptions,
                    position,
                    onItemClickListener
                )
            itemYapItSendMoneyBinding.executePendingBindings()
        }
    }
}
