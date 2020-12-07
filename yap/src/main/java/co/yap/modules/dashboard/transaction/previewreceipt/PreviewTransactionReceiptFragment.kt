package co.yap.modules.dashboard.transaction.previewreceipt

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants.FILE_PATH
import java.io.File

class PreviewTransactionReceiptFragment :
    BaseBindingFragment<IPreviewTransactionReceipt.ViewModel>(),
    IPreviewTransactionReceipt.View {
    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_preview_transaction_reseipt

    override val viewModel: IPreviewTransactionReceipt.ViewModel
        get() = ViewModelProviders.of(this).get(PreviewTransactionReceiptViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            bundle.getString(FILE_PATH)?.let {
                // image.setImageURI(Uri.fromFile(File(it)))
                viewModel.state.filePath = Uri.fromFile(File(it))
            }
        }
        registerObserver()
    }

    override fun registerObserver() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnSave -> {
                }
                R.id.tvRedo -> {
                }
            }
        })
    }

    override fun onDestroyView() {
        unRegisterObserver()
        super.onDestroyView()
    }

    override fun unRegisterObserver() {
        viewModel.clickEvent.removeObservers(this)
    }
}
