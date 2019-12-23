package co.yap.modules.dashboard.yapit.sendmoney.adapters

import android.content.Context
import androidx.databinding.ViewDataBinding
import co.yap.BR
import co.yap.databinding.ItemReasonListBinding
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.*
import co.yap.yapcore.BaseBindingArrayAdapter
import co.yap.yapcore.BaseBindingHolder

class ReasonListAdapter(
    context: Context,
    resource: Int,
    objects: List<ReasonList>
) :
    BaseBindingArrayAdapter<ReasonList, ReasonListAdapter.ViewHolder>(
        context,
        resource,
        objects
    ) {

    override fun createViewHolder(binding: ViewDataBinding): ViewHolder {
        return ViewHolder(binding)
    }


    inner class ViewHolder(binding: ViewDataBinding) : BaseBindingHolder(binding) {
        override fun getBindingVariable(): Int = BR.reasonList
        override fun bind(obj: Any, binding: ViewDataBinding?) {
            binding?.setVariable(getBindingVariable(), obj)
//            if(obj is ReasonList )
//
//            binding?.executePendingBindings()
        }
    }
}