package co.yap.modules.pdf

import android.content.Context
import android.content.Intent
import android.os.Bundle
import co.yap.yapcore.R
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.interfaces.BackPressImpl

class PDFActivity : DefaultActivity() {

    companion object {
        private const val URL = "URL"
        fun newIntent(context: Context, url: String): Intent {
            val intent = Intent(context, PDFActivity::class.java)
            intent.putExtra(URL, url)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)
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

            //val pdfView = findViewById<View>(R.id.pdfView) as PDFView
            //pdfView.fromFile("zan.pdf")

        } ?: close()
    }

    fun close() {
        showToast("Invalid file")
        finish()
    }

}
