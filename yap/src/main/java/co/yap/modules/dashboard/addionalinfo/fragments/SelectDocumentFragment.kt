package co.yap.modules.dashboard.addionalinfo.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.addionalinfo.interfaces.ISelectDocument
import co.yap.modules.dashboard.addionalinfo.viewmodels.SelectDocumentViewModel
import co.yap.yapcore.interfaces.OnItemClickListener

class SelectDocumentFragment : AdditionalInfoBaseFragment<ISelectDocument.ViewModel>(),
    ISelectDocument.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_select_document

    override val viewModel: ISelectDocument.ViewModel
        get() = ViewModelProviders.of(this).get(SelectDocumentViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        viewModel.uploadAdditionalDocumentAdapter.allowFullItemClickListener = true
        viewModel.uploadAdditionalDocumentAdapter.setItemListener(listener)
    }

    private val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
//            if (data is AdditionalDocument) {
            viewModel.uploadAdditionalDocumentAdapter.getDataList()[pos].isUploaded = !viewModel.uploadAdditionalDocumentAdapter.getDataList()[pos].isUploaded
            viewModel.uploadAdditionalDocumentAdapter.notifyItemChanged(pos)
//            }
        }
    }
}