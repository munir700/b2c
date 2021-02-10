package co.yap.modules.dashboard.transaction.previewreceipt

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants.FILE_PATH
import co.yap.yapcore.helpers.ExtraKeys
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
            bundle.getString(ExtraKeys.TRANSACTION_ID.name)?.let { id ->
                viewModel.transactionId = id
            }
        }
        registerObserver()
    }

    override fun registerObserver() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnSave -> {
                    viewModel.state.filePath?.let { uri ->
                        val file = File(uri.path ?: "")
                        viewModel.requestSavePicture(file) {
                            activity?.setResult(Activity.RESULT_OK)
                            activity?.finish()
                        }
                    }
                }
                R.id.tvRedo -> {
                    activity?.onBackPressed()
                }
                R.id.ivBack -> {
                    activity?.finish()
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
