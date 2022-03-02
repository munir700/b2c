package co.yap.modules.location.kyc_additional_info.employment_info.amendment

import androidx.recyclerview.widget.RecyclerView
import co.yap.networking.customers.responsedtos.employment_amendment.Document
import co.yap.yapcore.databinding.ItemEmploymentInfoDocumentBinding
import co.yap.yapcore.interfaces.OnItemClickListener

class DocumentItemViewHolder(
    private val itemBinding: ItemEmploymentInfoDocumentBinding
) :
    RecyclerView.ViewHolder(itemBinding.root) {
    var itemPosition = -1

    fun onBind(
        document: Document,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        itemPosition = position
        itemBinding.viewModel =
            DocumentItemViewModel(
                document,
                position,
                onItemClickListener
            )
        itemBinding.executePendingBindings()
    }
}
