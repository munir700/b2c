package co.yap.modules.imagepreviewer

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.translation.Translator
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.R
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener
import com.liveperson.infra.utils.picasso.Picasso

class ImagePreViewerActivity : BaseBindingActivity<IImagePreViewer.ViewModel>() {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_image_previewer

    override val viewModel: IImagePreViewer.ViewModel
        get() = ViewModelProviders.of(this).get(ImagePreViewerViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, clickEvent)

        viewModel.state.imageReceiptTitle?.set(
            intent?.getStringExtra(
                ExtraKeys.CONST_IMAGE_TITLE.name
            ) ?: "title"
        )

        viewModel.state.imageUrl?.set(
            intent?.getStringExtra(
                ExtraKeys.CONST_IMAGE_URL.name
            )
                ?: "https://scoopak.com/wp-content/uploads/2013/06/free-hd-natural-wallpapers-download-for-pc.jpg"
        )
        if (null!=intent?.getBundleExtra(ExtraKeys.CONST_IMAGE_URI.name))
        {
            var imageUri: Uri? = intent?.getBundleExtra(ExtraKeys.CONST_IMAGE_URI.name) as Uri
            viewModel.state.imageUri?.set(imageUri)

        }
    }


    var clickEvent = Observer<Int> {
        when (it) {

            R.id.ivActionShare -> {
            }

            R.id.ivActionDelete -> {
                deleteAlertDialog()
            }
        }
    }

    fun setResult() {
        val intent = Intent()
        intent.putExtra("key", "value")

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onBackPressed() {
        setResult()
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
                            setResult()
                        } else {
                            setResult()
                        }
                    }
                }
            }, isCancelable = false
        )
    }

}