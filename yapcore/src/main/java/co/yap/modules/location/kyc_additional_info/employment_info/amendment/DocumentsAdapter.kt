package co.yap.modules.location.kyc_additional_info.employment_info.amendment

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.networking.customers.responsedtos.employment_amendment.Document
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.R
import co.yap.yapcore.databinding.ItemEmploymentInfoDocumentBinding

class DocumentsAdapter(
    private val list: MutableList<Document>
) :
    BaseBindingRecyclerAdapter<Document, RecyclerView.ViewHolder>(list) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_employment_info_document

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return DocumentItemViewHolder(
            binding as ItemEmploymentInfoDocumentBinding
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is DocumentItemViewHolder) {
            holder.onBind(
                list[position],
                position,
                onItemClickListener
            )
        }
    }
}
