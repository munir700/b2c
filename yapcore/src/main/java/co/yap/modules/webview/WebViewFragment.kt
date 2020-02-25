package co.yap.modules.webview

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProviders
import co.yap.widgets.AdvancedWebView
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants.PAGE_URL
import co.yap.yapcore.constants.Constants.TOOLBAR_TITLE
import kotlinx.android.synthetic.main.fragment_webview.*


class WebViewFragment : BaseBindingFragment<IWebViewFragment.ViewModel>(), IWebViewFragment.View,
    AdvancedWebView.Listener {
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
        initAdvanceWebView()
//        initWebView()
//        setWebClient()

//        loadUrl(pageUrl ?: "")
    }


    private fun initAdvanceWebView() {
        webView?.setListener(activity, this)
        webView?.setGeolocationEnabled(false)
        webView?.setMixedContentAllowed(true)
        webView?.setCookiesEnabled(true)
        webView?.setThirdPartyCookiesEnabled(true)
        webView?.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView, url: String) {
                progressBar.visibility = ProgressBar.GONE
                // multiStateView.viewState = MultiStateView.ViewState.CONTENT
            }

        }
        webView?.webChromeClient = object : WebChromeClient() {

            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                progressBar.visibility = ProgressBar.GONE
                //multiStateView.viewState = MultiStateView.ViewState.CONTENT
            }
        }
        webView?.addHttpHeader("X-Requested-With", "")
        webView?.loadUrl(pageUrl ?: "")


    }

    override fun onPageStarted(url: String?, favicon: Bitmap?) {
        progressBar.visibility = ProgressBar.VISIBLE

    }

    override fun onPageFinished(url: String?) {
        progressBar.visibility = ProgressBar.GONE
    }

    override fun onPageError(errorCode: Int, description: String?, failingUrl: String?) {
    }

    override fun onDownloadRequested(
        url: String?,
        suggestedFilename: String?,
        mimeType: String?,
        contentLength: Long,
        contentDisposition: String?,
        userAgent: String?
    ) {
    }

    override fun onExternalPageRequest(url: String?) {
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return false
    }
    //    @SuppressLint("SetJavaScriptEnabled")
//    private fun initWebView() {
//        webView.settings.javaScriptEnabled = true
//        webView.settings.loadWithOverviewMode = true
//        webView.settings.useWideViewPort = true
//        webView.settings.domStorageEnabled = true
//        webView.webViewClient = object : WebViewClient() {
//            override
//            fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
////                val serverCertificate: SslCertificate = error?.certificate!!
//                handler?.proceed()
//            }
//
//        }
//    }
//
//    private fun setWebClient() {
//        webView.webChromeClient = object : WebChromeClient() {
//            override fun onProgressChanged(
//                view: WebView,
//                newProgress: Int
//            ) {
//                super.onProgressChanged(view, newProgress)
//                progressBar.progress = newProgress
//                if (newProgress < 100 && progressBar.visibility == ProgressBar.GONE) {
//                    progressBar.visibility = ProgressBar.VISIBLE
//                }
//                if (newProgress == 100) {
//                    progressBar.visibility = ProgressBar.GONE
//                }
//            }
//        }
//
//    }

//    private fun loadUrl(pageUrl: String) {
//        webView.loadUrl(pageUrl)
//    }


    @SuppressLint("NewApi")
    override fun onResume() {
        super.onResume()
        webView?.onResume()
        // ...
    }

    @SuppressLint("NewApi")
    override fun onPause() {
        webView?.onPause()
        // ...
        super.onPause()
    }

    override fun onDestroy() {
        webView?.onDestroy()
        // ...
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        webView?.onActivityResult(requestCode, resultCode, intent)
        // ...
    }
}