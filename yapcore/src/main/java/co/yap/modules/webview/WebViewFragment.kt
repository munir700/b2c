package co.yap.modules.webview

import android.annotation.SuppressLint
import android.net.http.SslCertificate
import android.net.http.SslError
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProviders
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants.PAGE_URL
import co.yap.yapcore.constants.Constants.TOOLBAR_TITLE
import kotlinx.android.synthetic.main.fragment_webview.*


class WebViewFragment : BaseBindingFragment<IWebViewFragment.ViewModel>(), IWebViewFragment.View {
    override fun getBindingVariable() = BR.webViewFragmentViewModel

    override fun getLayoutId() = R.layout.fragment_webview
    private var pageUrl: String? = null
    private var title: String? = null
    override val viewModel: IWebViewFragment.ViewModel
        get() = ViewModelProviders.of(this).get(WebViewFragmentViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            pageUrl = it.getString(PAGE_URL, "")
            title = it.getString(TOOLBAR_TITLE, "")
        }
        initWebView()
        setWebClient()

        loadUrl(pageUrl ?: "")
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
//                val serverCertificate: SslCertificate = error?.certificate!!
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
                if (newProgress < 100 && progressBar.visibility == ProgressBar.GONE) {
                    progressBar.visibility = ProgressBar.VISIBLE
                }
                if (newProgress == 100) {
                    progressBar.visibility = ProgressBar.GONE
                }
            }
        }

    }

    private fun loadUrl(pageUrl: String) {
        webView.loadUrl(pageUrl)
    }
}