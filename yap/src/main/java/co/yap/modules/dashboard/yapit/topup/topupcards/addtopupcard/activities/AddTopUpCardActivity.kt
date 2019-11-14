package co.yap.modules.dashboard.yapit.topup.topupcards.addtopupcard.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.dashboard.yapit.topup.topupcards.addtopupcard.interfaces.IAddTopUpCard
import co.yap.modules.dashboard.yapit.topup.topupcards.addtopupcard.viewmodels.AddTopUpCardViewModel
import co.yap.yapcore.BaseActivity
import co.yap.yapcore.constants.Constants
import kotlinx.android.synthetic.main.activity_add_top_up_card.*

class AddTopUpCardActivity : BaseActivity<IAddTopUpCard.ViewModel>(), IAddTopUpCard.View {
    var alias: String? = null
    var cardColor: String? = null
    var sessionId: String? = null

    override val viewModel: IAddTopUpCard.ViewModel
        get() = ViewModelProviders.of(this).get(AddTopUpCardViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_top_up_card)
        setObservers()
        setupWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        wb.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                request?.let {
                    if (request.url.toString().startsWith("yap-app://")) {
                        onNewIntent(intent)
                        sessionId = request.url.getQueryParameter("sessionID")
                        cardColor = request.url.getQueryParameter("color")
                        alias = request.url.getQueryParameter("alias")
                        view?.visibility = View.GONE
                        viewModel.addTopUpCard(
                            sessionId.toString(),
                            alias.toString(),
                            "#$cardColor"
                        )
                        return true
                    }
                }
                return false
            }
        }
        wb.settings.javaScriptEnabled = true
        wb.settings.setSupportZoom(true)
        wb.loadUrl(Constants.URL_ADD_TOPUP_CARD)
    }

    fun setObservers() {
        viewModel.isCardAdded.observe(this, Observer {
            setData(it)
            finish()
        })
    }

    private fun setData(isCardAdded: Boolean) {
        val intent = Intent()
        intent.putExtra("isCardAdded", isCardAdded)
        setResult(Activity.RESULT_OK, intent)
    }
}
