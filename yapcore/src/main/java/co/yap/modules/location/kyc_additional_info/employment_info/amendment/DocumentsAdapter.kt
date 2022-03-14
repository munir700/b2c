package co.yap.modules.location.kyc_additional_info.employment_info.amendment

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.networking.customers.responsedtos.employment_amendment.Document
import co.yap.yapcore.BR
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder

class DocumentsAdapter(
    val list: MutableList<Document>, navigation: NavController?
) :
    BaseRVAdapter<Document, DocumentItemViewModel, BaseViewHolder<Document, DocumentItemViewModel>>(
        list,
        navigation
    ) {

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getLayoutId(viewType: Int): Int = getViewModel(viewType).layoutRes()
    override fun getViewHolder(
        view: View,
        viewModel: DocumentItemViewModel,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ) = BaseViewHolder(view, viewModel, mDataBinding)

    override fun getViewModel(viewType: Int) = DocumentItemViewModel()

    override fun getVariableId() = BR.viewModel
}
