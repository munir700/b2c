package co.yap.modules.imagepreviewer

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.app.YAPApplication
import co.yap.translation.Translator
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.R
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.createTempFile
import co.yap.yapcore.helpers.extentions.takeScreenshotForView
import co.yap.yapcore.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.activity_image_previewer.*
import java.io.FileOutputStream

class ImagePreViewerActivity : BaseBindingActivity<IImagePreViewer.ViewModel>() {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_image_previewer

    override val viewModel: IImagePreViewer.ViewModel
        get() = ViewModelProviders.of(this).get(ImagePreViewerViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showLoader(true)
        viewModel.clickEvent.observe(this, clickEvent)
        setDataArguments(intent)
    }

    private fun setDataArguments(intent: Intent) {
        viewModel.state.imageReceiptTitle?.set(
            intent?.getStringExtra(
                ExtraKeys.CONST_IMAGE_TITLE.name
            ) ?: ""
        )
        viewModel.state.imageUrl?.set(
            intent?.getStringExtra(
                ExtraKeys.CONST_IMAGE_URL.name
            )
        )
        if (viewModel.state.imageUrl?.get().isNullOrEmpty()) showLoader(false)
        viewModel.receiptId = viewModel.state.imageUrl?.get()?.split("/")?.last() ?: ""
        viewModel.transactionId = intent?.getStringExtra(ExtraKeys.TRANSACTION_ID.name) ?: ""
    }

    var clickEvent = Observer<Int> {
        when (it) {
            R.id.ivActionShare -> {
                shareImage(imageViewConatiner)
            }

            R.id.ivActionDelete -> {
                deleteAlertDialog()
            }
        }
    }

    fun setResult() {
        val intent = Intent()
//        intent.putExtra("key", "value")
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> finish()

        }
    }

    private fun deleteAlertDialog() {
        Utils.confirmationDialog(
            this,
            null,
            Translator.getString(
                this,
                R.string.screen_image_previewer_display_text_delete_message
            ), Translator.getString(
                this,
                R.string.screen_image_previewer_button_text_delete
            ), Translator.getString(
                this,
                R.string.common_button_cancel
            ),
            object : OnItemClickListener {
                override fun onItemClick(view: View, data: Any, pos: Int) {
                    if (data is Boolean) {
                        if (data) {
                            viewModel.deleteReceipt() {
                                setResult()
                            }
                        } else {
                            setResult()
                        }
                    }
                }
            }, isCancelable = false
        )
    }


    fun shareImage(rootView: View) {
        val bitmap: Bitmap = takeScreenshotForView(rootView)
        val imageName = "YAP-Image"
        var bmpUri: Uri? = null
        val fileName = "${imageName}.jpg"
        val file = createTempFile(fileName)
        try {
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        bmpUri = FileProvider.getUriForFile(
            this,
            YAPApplication.configManager?.applicationId + ".provider", file
        );

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        //need to update the text
//        shareIntent.putExtra(
//            Intent.EXTRA_TEXT,
//            "Hi! its ${SessionManager.user?.currentCustomer?.getFullName() ?: "YAP User"}  \nHere is my YAP QR Code, please scan it for money transactions."
//        )
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri)
        shareIntent.type = "image/jpeg"
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(Intent.createChooser(shareIntent, "YAP Image"))
    }
}