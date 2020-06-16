package co.yap.modules.pdf

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Build
import android.os.Bundle
import android.os.ParcelFileDescriptor
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.R
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.interfaces.BackPressImpl
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import kotlinx.android.synthetic.main.activity_pdf.*
import java.io.File
import java.io.IOException

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

    private val pageIndex = 0
    private var pdfRenderer: PdfRenderer? = null
    private var currentPage: PdfRenderer.Page? = null
    private var parcelFileDescriptor: ParcelFileDescriptor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupData()
        viewModel.clickEvent.observe(this, listener)
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
//                try {
//                    openRenderer(file)
//                    showPage(pageIndex)
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
            }
        } ?: close()
    }


    val listener = Observer<Int> {
        when (it) {
//            R.id.button_pre_doc -> {
//                showPage(currentPage?.index ?: 0 - 1)
//
//            }
//            R.id.button_next_doc -> {
//                showPage(currentPage?.index ?: 0 + 1)
//            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun showPage(index: Int) {
        if (pdfRenderer?.pageCount ?: 0 <= index) {
            return
        }
        // Make sure to close the current page before opening another one.
        if (null != currentPage) {
            currentPage?.close()
        }
        // Use `openPage` to open a specific page in PDF.
        currentPage = pdfRenderer?.openPage(index)
        // Important: the destination bitmap must be ARGB (not RGB).
//        val bitmap: Bitmap = Bitmap.createBitmap(
//            currentPage?.width ?: 0, currentPage?.height ?: 0,
//            Bitmap.Config.ARGB_8888
//        )

        val bitmap = Bitmap.createBitmap(
            resources.displayMetrics.densityDpi * currentPage!!.width / 72,
            resources.displayMetrics.densityDpi * currentPage!!.height / 72,
            Bitmap.Config.ARGB_8888
        )

        // Here, we render the page onto the Bitmap.
// To render a portion of the page, use the second and third parameter. Pass nulls to get
// the default result.
// Pass either RENDER_MODE_FOR_DISPLAY or RENDER_MODE_FOR_PRINT for the last parameter.
        currentPage?.render(
            bitmap,
            null,
            null,
            PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
        )
        // We are ready to show the Bitmap to user.
        pdf_image.setImageBitmap(bitmap)
        updateUi()
    }

    /**
     * Updates the state of 2 control buttons in response to the current page index.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun updateUi() {
        val index = currentPage?.index
        val pageCount = pdfRenderer?.pageCount
        //prePageButton.setEnabled(0 != index)
        //nextPageButton.setEnabled(index + 1 < pageCount)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun getPageCount(): Int {
        return pdfRenderer!!.pageCount
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onStop() {
        try {
            closeRenderer()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        super.onStop()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Throws(IOException::class)
    private fun openRenderer(
        file: File?
    ) { // In this sample, we read a PDF from the assets directory.
        parcelFileDescriptor = ParcelFileDescriptor.open(
            file,
            ParcelFileDescriptor.MODE_READ_ONLY
        )
        // This is the PdfRenderer we use to render the PDF.
        if (parcelFileDescriptor != null) {
            pdfRenderer = PdfRenderer(parcelFileDescriptor)
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }

    private fun close() {
        showToast("Invalid file")
        finish()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Throws(IOException::class)
    private fun closeRenderer() {
        currentPage?.close()
        pdfRenderer?.close()
        parcelFileDescriptor?.close()
    }

}
