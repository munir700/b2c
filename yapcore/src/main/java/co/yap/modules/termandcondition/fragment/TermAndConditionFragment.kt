package co.yap.modules.termandcondition.fragment

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.termandcondition.interfaces.ITermAndCondition
import co.yap.modules.termandcondition.viewmodel.TermAndConditionViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import kotlinx.android.synthetic.main.fragment_term_and_condition.*


class TermAndConditionFragment : BaseBindingFragment<ITermAndCondition.ViewModel>(),
    ITermAndCondition.View {

    override fun getBindingVariable(): Int = BR.houseHoldViewModel

    override fun getLayoutId(): Int = R.layout.fragment_term_and_condition

    override val viewModel: ITermAndCondition.ViewModel
        get() = ViewModelProviders.of(this).get(TermAndConditionViewModel::class.java)

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadTermAndCondition()
    }

    private fun loadTermAndCondition() {
        termConditionWebView.settings.javaScriptEnabled = true
        termConditionWebView.webViewClient = object : WebViewClient() {
            override
            fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
//                val serverCertificate: SslCertificate = error?.certificate!!
                handler?.proceed()
            }

        }
        termConditionWebView.loadUrl("https://yap.co/")
    }

}
