package co.yap.modules.dashboard.addionalinfo.adapters

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemUploadAdditionalDocumentBinding
import co.yap.modules.dashboard.addionalinfo.model.AdditionalDocument
import co.yap.modules.dashboard.addionalinfo.viewmodels.UploadAdditionalDocumentItemViewModel
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.interfaces.OnItemClickListener

class UploadAdditionalDocumentAdapter(
    context: Context,
    private val list: MutableList<AdditionalDocument>
) : BaseBindingRecyclerAdapter<AdditionalDocument, UploadAdditionalDocumentAdapter.ViewHolder>(list) {


    override fun getLayoutIdForViewType(viewType: Int): Int =
        R.layout.item_upload_additional_document

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.onBind(list[position], position, onItemClickListener)
    }

    class ViewHolder(private val itemUploadAdditionalDocumentBinding: ItemUploadAdditionalDocumentBinding) :
        RecyclerView.ViewHolder(itemUploadAdditionalDocumentBinding.root) {
        fun onBind(
            additionalDocument: AdditionalDocument,
            position: Int,
            onItemClickListener: OnItemClickListener?
        ) {

            itemUploadAdditionalDocumentBinding.viewModel =
                UploadAdditionalDocumentItemViewModel(
                    additionalDocument,
                    position,
                    onItemClickListener
                )
            itemUploadAdditionalDocumentBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(binding: ViewDataBinding): ViewHolder {
        return ViewHolder(binding as ItemUploadAdditionalDocumentBinding)
    }
}