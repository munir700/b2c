package co.yap.modules.dashboard.transaction.receipt.viewer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivityImagePreviewerBinding
import co.yap.networking.transactions.responsedtos.ReceiptModel
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.confirm
import co.yap.yapcore.helpers.extentions.shareImage

class ImageViewerActivity : BaseBindingActivity<ActivityImagePreviewerBinding,IImageViewer.ViewModel>(), IImageViewer.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_image_previewer

    override val viewModel: IImageViewer.ViewModel
        get() = ViewModelProviders.of(this).get(ImageViewerViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, clickEvent)
        setDataArguments(intent)
        viewDataBinding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.receiptId =
                    viewModel.imagesViewerAdapter.getDataForPosition(position).receiptId
                viewModel.state.imageReceiptTitle?.set("receipt ${position.plus(1)}")
            }
        })
    }

    private fun setDataArguments(intent: Intent) {
        viewModel.transactionId = intent.getStringExtra(ExtraKeys.TRANSACTION_ID.name) ?: ""
        if (intent.hasExtra(ExtraKeys.TRANSACTION_RECEIPT_LIST.name)) {
            val receipts =
                intent.getParcelableArrayListExtra<ReceiptModel>(ExtraKeys.TRANSACTION_RECEIPT_LIST.name) as ArrayList<ReceiptModel>
            viewModel.imagesViewerAdapter.setList(receipts)
            val selectedReceipt =
                intent.getParcelableExtra<ReceiptModel>(ExtraKeys.TRANSACTION_RECEIPT.name)
            val currentImagePos =
                viewModel.imagesViewerAdapter.getDataList().indexOf(selectedReceipt)

            viewModel.state.imageReceiptTitle?.set("receipt ${currentImagePos.plus(1)}")
            viewDataBinding.viewPager.currentItem = currentImagePos
            viewDataBinding.viewPager.setCurrentItem(currentImagePos,false)
            viewModel.receiptId = selectedReceipt?.receiptId?:""
        } else {
            finish()
        }
    }

    var clickEvent = Observer<Int> {
        when (it) {
            R.id.ivActionShare -> {
                shareImage(
                    viewDataBinding.viewPager,
                    imageName = shareReceiptImageName,
                    chooserTitle = shareReceiptTitle
                )
            }

            R.id.ivActionDelete -> {
                deleteAlertDialog()
            }
        }
    }

    private fun deleteAlertDialog() {
        confirm(
            message = getString(Strings.screen_image_previewer_display_text_delete_message),
            positiveButton = getString(Strings.screen_image_previewer_button_text_delete),
            negativeButton = getString(Strings.common_button_cancel),
            cancelable = false
        ) {
            viewModel.deleteReceipt {
                if (viewModel.imagesViewerAdapter.itemCount == 1) {
                    setResult()
                } else {
                    viewModel.imagesViewerAdapter.removeItemAt(viewDataBinding.viewPager.currentItem)
                }
            }
        }
    }

    fun setResult() {
        if (viewModel.isReceiptDeleted) {
            val intent = Intent()
            setResult(Activity.RESULT_OK, intent)
            finish()
        } else {
            finish()
        }
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> setResult()
        }
    }

    override fun onBackPressed() {
        setResult()
    }
}