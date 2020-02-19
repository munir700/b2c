package co.yap.modules.webview

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import co.yap.yapcore.R
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {
    companion object {
        const val PAGE_URL = ""
        const val TOOL_BAR_TITLE = ""
        const val MAX_PROGRESS = 100
    }

    private var pageUrl: String? = null
    private var title: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!intent.hasExtra(PAGE_URL)) finish()
        pageUrl = intent.getStringExtra(PAGE_URL)
        if (pageUrl.isNullOrEmpty()) finish()
        setContentView(R.layout.activity_web)


        if (intent.hasExtra(TOOL_BAR_TITLE)) {
            title = intent.getStringExtra(TOOL_BAR_TITLE)
            findViewById<AppCompatTextView>(R.id.tvTitle).text = title

        }
        initWebView()
        setWebClient()



        handlePullToRefresh()
        loadUrl(pageUrl ?: "")
    }

    fun backClick(view: View) {
        this.finish()
    }

//    FinestWebView.Builder(it)
//    .titleDefault(title ?: "")
//    .updateTitleFromHtml(true)
//    .toolbarScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
//    .gradientDivider(false)
//    .dividerHeight(2)
//    .titleColor(ContextCompat.getColor(it, R.color.colorPrimaryDark))
//    .toolbarColorRes(R.color.colorWhite)
//    .dividerColorRes(R.color.colorPrimaryDark)
//    .iconDefaultColorRes(R.color.colorPrimary)
//    .iconDisabledColorRes(R.color.light_grey)
//    .iconPressedColorRes(R.color.colorPrimaryDark)
//    .progressBarHeight(convertDpToPx(it, 3f))
//    .progressBarColorRes(R.color.colorPrimaryDark)
//    .backPressToClose(false)
//    .webViewUseWideViewPort(true)
//    .webViewSupportZoom(true)
//    .webViewBuiltInZoomControls(true)
//    .setCustomAnimations(
//    R.anim.activity_open_enter,
//    R.anim.activity_open_exit,
//    R.anim.activity_close_enter,
//    R.anim.activity_close_exit
//    )
//    .show(url)


    private fun handlePullToRefresh() {
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override
            fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed()
            }
        }
    }

    private fun setWebClient() {
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(
                view: WebView,
                newProgress: Int
            ) {
                super.onProgressChanged(view, newProgress)
                progressBar.progress = newProgress
                if (newProgress < MAX_PROGRESS && progressBar.visibility == ProgressBar.GONE) {
                    progressBar.visibility = ProgressBar.VISIBLE
                }
                if (newProgress == MAX_PROGRESS) {
                    progressBar.visibility = ProgressBar.GONE
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, exit the activity)
        return super.onKeyDown(keyCode, event)
    }

    private fun loadUrl(pageUrl: String) {
        webView.loadUrl(pageUrl)
    }
}