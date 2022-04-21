package co.yap.modules.webview

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import co.yap.translation.Strings
import co.yap.widgets.AdvancedWebView
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants.PAGE_URL
import co.yap.yapcore.constants.Constants.TOOLBAR_TITLE
import co.yap.yapcore.databinding.FragmentWebviewBinding
import co.yap.yapcore.helpers.extentions.makeCall
import co.yap.yapcore.helpers.extentions.sendEmail
import co.yap.yapcore.helpers.permissions.PermissionHelper

class WebViewFragment : BaseBindingFragment<FragmentWebviewBinding, IWebViewFragment.ViewModel>(),
    IWebViewFragment.View,
    AdvancedWebView.Listener, View.OnKeyListener {
    override fun getBindingVariable() = BR.webViewFragmentViewModel

    override fun getLayoutId() = R.layout.fragment_webview
    private var pageUrl: String? = null
    private var title: String? = null
    var permissionHelper: PermissionHelper? = null

    override val viewModel: IWebViewFragment.ViewModel
        get() = ViewModelProvider(this).get(WebViewFragmentViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            pageUrl = it.getString(PAGE_URL, "")
            title = it.getString(
                TOOLBAR_TITLE,
                getString(Strings.screen_help_support_display_text_title)
            )
        }
        viewModel.state.toolbarTitle =
            title ?: getString(
                Strings.screen_help_support_display_text_title
            )
        initAdvanceWebView()

    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                if (viewDataBinding.webView.canGoBack()) {
                    viewDataBinding.webView.goBack()
                } else {
                    activity?.finish()
                }
            }
        }
    }

    private fun initAdvanceWebView() {
        viewDataBinding.webView.setListener(activity, this)
        viewDataBinding.webView.setGeolocationEnabled(false)
        viewDataBinding.webView.setMixedContentAllowed(true)
        viewDataBinding.webView.setCookiesEnabled(true)
        viewDataBinding.webView.setThirdPartyCookiesEnabled(true)
        viewDataBinding.webView.setOnKeyListener(this)
        viewDataBinding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                if (view.title.equals(""))
                    view.reload()
                viewDataBinding.progressBar.visibility = ProgressBar.GONE
            }
        }
        viewDataBinding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                updateProgress(newProgress)

            }

            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                viewDataBinding.progressBar.visibility = ProgressBar.GONE
            }

        }
        viewDataBinding.webView.addHttpHeader("X-Requested-With", "")
        viewDataBinding.webView.loadUrl(pageUrl ?: "")
    }

    private fun updateProgress(newProgress: Int) {
        with(viewDataBinding.progressBar) {
            progress = newProgress
            visibility = if (newProgress < 100) ProgressBar.VISIBLE else ProgressBar.GONE
        }
    }

    override fun onPageStarted(url: String?, favicon: Bitmap?) {
        viewDataBinding.progressBar.visibility = ProgressBar.VISIBLE
    }

    override fun onPageFinished(url: String?) {
        viewDataBinding.progressBar.visibility = ProgressBar.GONE
    }

    override fun onPageError(errorCode: Int, description: String?, failingUrl: String?) {
        viewDataBinding.progressBar.visibility = ProgressBar.GONE
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
    }

    override fun onExternalPageRequest(url: String?) {
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        if (request?.url.toString().startsWith("mailto")) {
            requireContext().sendEmail(
                email = request?.url.toString().replaceFirst("mailto:", "")
            )
            return true
        } else {
            if (request?.url.toString().startsWith("tel:")) {
                requireContext().makeCall(request?.url.toString().replaceFirst("tel:", ""))
                return true
            }
        }
        return false
    }

    @SuppressLint("NewApi")
    override fun onResume() {
        super.onResume()
        viewDataBinding.webView.onResume()
    }

    @SuppressLint("NewApi")
    override fun onPause() {
        viewDataBinding.webView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        viewDataBinding.webView.onDestroy()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        viewDataBinding.webView.onActivityResult(requestCode, resultCode, intent)
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
                    } else {
                        showToast("Unable to download file")
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

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK
            && event?.action == MotionEvent.ACTION_UP
            && viewDataBinding.webView.canGoBack()
        ) {
            viewDataBinding.webView.goBack()
            return true
        }
        return false
    }
}
