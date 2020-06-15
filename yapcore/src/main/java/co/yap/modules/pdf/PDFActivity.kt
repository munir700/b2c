package co.yap.modules.pdf

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.R
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.interfaces.BackPressImpl
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle

class PDFActivity : BaseBindingActivity<IPDFActivity.ViewModel>(), IPDFActivity.View {

    companion object {
        private const val URL = "URL"
        fun newIntent(context: Context, url: String): Intent {
            val intent = Intent(context, PDFActivity::class.java)
            intent.putExtra(URL, url)
            return intent
        }
    }

    override val viewModel: IPDFActivity.ViewModel
        get() = ViewModelProviders.of(this).get(PDFViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_pdf

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupData()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }

    private fun setupData() {
        val url = intent?.getValue(URL, ExtraType.STRING.name) as? String
        url?.let {
            viewModel.downloadFile(it) { file ->
                val pdfView = findViewById<PDFView>(R.id.pdfView)
                pdfView.fromFile(file)
                    .defaultPage(0)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    //.onPageChange(this)
                    .enableAnnotationRendering(true)
                    .onLoad {
                    }
                    .scrollHandle(DefaultScrollHandle(this))
                    .load()
            }
        } ?: close()
    }

    fun close() {
        showToast("Invalid file")
        finish()
    }

}
