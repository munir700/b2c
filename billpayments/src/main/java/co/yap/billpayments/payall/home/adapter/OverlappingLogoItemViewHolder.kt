package co.yap.billpayments.payall.home.adapter

import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.databinding.LayoutItemPayAllLogoBinding
import co.yap.networking.customers.responsedtos.billpayment.BillerInfoModel
import co.yap.yapcore.interfaces.OnItemClickListener

class OverlappingLogoItemViewHolder(private val layoutItemPayAllLogoBinding: LayoutItemPayAllLogoBinding) :
    RecyclerView.ViewHolder(layoutItemPayAllLogoBinding.root) {

    fun onBind(
        billerInfoModel: BillerInfoModel,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        layoutItemPayAllLogoBinding.viewModel =
            OverlappingLogoItemViewModel(
                billerInfoModel,
                position,
                onItemClickListener
            )
        layoutItemPayAllLogoBinding.executePendingBindings()
    }
}
