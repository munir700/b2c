package co.yap.modules.dashboard.yapit.sendmoney.adapters

import android.content.Context
import androidx.databinding.ViewDataBinding
import co.yap.BR
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.yapcore.BaseBindingArrayAdapter
import co.yap.yapcore.BaseBindingHolder

class ReasonListAdapter(
    context: Context,
    resource: Int,
    objects: List<InternationalFundsTransferReasonList.ReasonList>
) :
    BaseBindingArrayAdapter<InternationalFundsTransferReasonList.ReasonList, ReasonListAdapter.ViewHolder>(
        context,
        resource,
        objects
    ) {


    override fun createViewHolder(binding: ViewDataBinding): ViewHolder {
        return ViewHolder(binding)
    }


    inner class ViewHolder(binding: ViewDataBinding) : BaseBindingHolder(binding) {
        override fun bind(obj: Object, binding: ViewDataBinding?) {
             //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getBindingVariable(): Int = BR.reasonList
        private fun onBind(binding: Object) {
        }
    }
}