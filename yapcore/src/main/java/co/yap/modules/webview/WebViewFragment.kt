package co.yap.modules.webview

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.DialogInterface.OnKeyListener
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.widgets.AdvancedWebView
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants.PAGE_URL
import co.yap.yapcore.constants.Constants.TOOLBAR_TITLE
import co.yap.yapcore.helpers.extentions.isWhatsAppInstalled
import co.yap.yapcore.helpers.extentions.makeCall
import co.yap.yapcore.helpers.extentions.openUrl
import co.yap.yapcore.helpers.extentions.openWhatsApp
import co.yap.yapcore.helpers.permissions.PermissionHelper
import kotlinx.android.synthetic.main.fragment_webview.*


class WebViewFragment : BaseBindingFragment<IWebViewFragment.ViewModel>(), IWebViewFragment.View,
    AdvancedWebView.Listener, View.OnKeyListener {
    override fun getBindingVariable() = BR.webViewFragmentViewModel

    override fun getLayoutId() = R.layout.fragment_webview
    private var pageUrl: String? = null
    private var title: String? = null
    var permissionHelper: PermissionHelper? = null

    override val viewModel: IWebViewFragment.ViewModel
        get() = ViewModelProviders.of(this).get(WebViewFragmentViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            pageUrl = it.getString(PAGE_URL, "")
            title = it.getString(TOOLBAR_TITLE, "")
        }
        initAdvanceWebView()
        setObservers()
//        initWebView()
//        setWebClient()

//        loadUrl(pageUrl ?: "")
    }

    fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
    }

    private val observer = Observer<Int> {
        when (it) {
            R.id.tbBtnBack -> {

                if (webView.canGoBack()) {
                    webView.goBack()
                }
                else
                {
                    activity?.finish()
                }
            }

        }
    }

    private fun initAdvanceWebView() {
        webView?.setListener(activity, this)
        webView?.setGeolocationEnabled(false)
        webView?.setMixedContentAllowed(true)
        webView?.setCookiesEnabled(true)
        webView?.setThirdPartyCookiesEnabled(true)
        webView?.setOnKeyListener(this)






        webView?.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView, url: String) {
                progressBar?.let {
                    it.visibility = ProgressBar.GONE
                }


                // multiStateView.viewState = MultiStateView.ViewState.CONTENT

            }


        }
        webView?.webChromeClient = object : WebChromeClient() {

            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                progressBar?.let {
                    it.visibility = ProgressBar.GONE
                }
                //multiStateView.viewState = MultiStateView.ViewState.CONTENT
            }

        }
        webView?.addHttpHeader("X-Requested-With", "")
        webView?.loadUrl(pageUrl ?: "")
    }


    override fun onPageStarted(url: String?, favicon: Bitmap?) {
        progressBar?.let {
            it.visibility = ProgressBar.VISIBLE
        }
    }

    override fun onPageFinished(url: String?) {
        progressBar?.let {
            it.visibility = ProgressBar.GONE
        }
    }

    override fun onPageError(errorCode: Int, description: String?, failingUrl: String?) {
        progressBar?.let {
            it.visibility = ProgressBar.GONE
        }
    }

    override fun onDownloadRequested(
        url: String?,
        suggestedFilename: String?,
        mimeType: String?,
        contentLength: Long,
        contentDisposition: String?,
        userAgent: String?
    ) {
        checkPermission(url, suggestedFilename)
//        // some file is available for download
//        // either handle the download yourself or use the code below
//        if (AdvancedWebView.handleDownload(requireContext(), url, suggestedFilename)) {
//            // download successfully handled
//        } else {
//            // download couldn't be handled because user has disabled download manager app on the device
//            // TODO show some notice to the user
//        }
    }

    override fun onExternalPageRequest(url: String?) {
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        if (request!!.url.toString().startsWith("tel:")) {
            callPhoneNumber(request.url.toString())
            return true
        } else {
            if (request.url.toString().startsWith("mailto")) {
                sendEmail(request.url.toString())
                return true
            }

        }
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

    private fun checkPermission(url: String?, suggestedFilename: String?) {
        permissionHelper = PermissionHelper(
            this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 100
        )
        permissionHelper?.request(object : PermissionHelper.PermissionCallback {
            override fun onPermissionGranted() {
                if (url != null && suggestedFilename != null)
                    if (AdvancedWebView.handleDownload(requireContext(), url, suggestedFilename)) {
                        // download successfully handled
                    } else {
                        showToast("Unable to download file")
                        // download couldn't be handled because user has disabled download manager app on the device
                        // TODO show some notice to the user
                    }
            }

            override fun onIndividualPermissionGranted(grantedPermission: Array<String>) {
                showToast("Can't proceed without permissions")
            }

            override fun onPermissionDenied() {
                showToast("Can't proceed without permissions")
            }

            override fun onPermissionDeniedBySystem() {
                showToast("Can't proceed without permissions")
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHelper?.onRequestPermissionsResult(
            requestCode,
            permissions as Array<String>,
            grantResults
        )
    }

    private fun callPhoneNumber(url: String) {

        val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
        startActivity(intent)


    }

    private fun sendEmail(url: String) {

        val mail = url.replaceFirst("mailto:", "")
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.type = "text/plain"
        intent.data = Uri.parse("mailto:$mail")
        intent.putExtra(Intent.EXTRA_EMAIL, mail)

        startActivity(Intent.createChooser(intent, "Send Email"))
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK
            && event!!.action == MotionEvent.ACTION_UP
            && webView.canGoBack()
        ) {
            webView.goBack()
            return true
        }
        return false
    }


}
