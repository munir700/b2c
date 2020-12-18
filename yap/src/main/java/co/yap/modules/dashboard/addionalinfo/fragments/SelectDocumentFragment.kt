package co.yap.modules.dashboard.addionalinfo.fragments

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.addionalinfo.interfaces.ISelectDocument
import co.yap.modules.dashboard.addionalinfo.viewmodels.SelectDocumentViewModel
import co.yap.networking.customers.models.additionalinfo.AdditionalDocument
import co.yap.yapcore.helpers.extentions.startFragmentForResult
import co.yap.yapcore.interfaces.OnItemClickListener
import java.io.File

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
            if (data is AdditionalDocument) {
                if (data.isUploaded == false)
                    startFragmentForResult<AdditionalInfoScanDocumentFragment>(fragmentName = AdditionalInfoScanDocumentFragment::class.java.name) { resultCode, data ->
                        if (resultCode == Activity.RESULT_OK) {
                            data?.let {
                                val file: File? = it.extras?.get("file") as File
                            }
                            viewModel.uploadAdditionalDocumentAdapter.getDataList()[pos].isUploaded =
                                !(viewModel.uploadAdditionalDocumentAdapter.getDataList()[pos].isUploaded
                                    ?: false)
                            viewModel.uploadAdditionalDocumentAdapter.notifyItemChanged(pos)
                        }
                    }
            }
        }
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.btnNext -> {
                viewModel.moveToNext()
                findNavController().navigate(R.id.action_selectDocumentFragment_to_additionalInfoQuestion)
            }
        }
    }
}