package co.yap.modules.dashboard.yapit.topup.topupcards.addtopupcard.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.*
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
    var type: String? = null

    companion object {
        fun newIntent(context: Context, url: String, type: String): Intent {
            val intent = Intent(context, AddTopUpCardActivity::class.java)
            intent.putExtra(Constants.KEY, url)
            intent.putExtra(Constants.TYPE, type)
            return intent
        }
    }


    override val viewModel: IAddTopUpCard.ViewModel
        get() = ViewModelProviders.of(this).get(AddTopUpCardViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_top_up_card)
        if (intent.hasExtra(Constants.KEY) && intent.hasExtra(Constants.TYPE)) {
            viewModel.state.url = intent.getStringExtra(Constants.KEY)
            type = intent.getStringExtra(Constants.TYPE)
            if (type == Constants.TYPE_ADD_CARD) {
                setupWebViewForAddCard()
            } else if (type == Constants.TYPE_TOP_UP_TRANSACTION) {
                setupWebViewForTopUpCardTransaction()
            }
        }
        setObservers()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebViewForAddCard() {
        wb.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                request?.let {
                  println("request is ${request.url.toString()}")
                    if (request.url.toString().startsWith("yap-app://")) {
                        onNewIntent(intent)
                        sessionId = request.url.getQueryParameter("sessionID")
                        cardColor = request.url.getQueryParameter("color")
                        alias = request.url.getQueryParameter("alias")
                        view?.visibility = View.GONE
                        viewModel.addTopUpCard(
                            sessionId.toString(),
                            alias.toString(),
                            cardColor.toString()
                        )
                        return true
                    }
                }
                return false
            }
        }
        wb.settings.javaScriptEnabled = true
        wb.settings.setSupportZoom(true)
        wb.loadUrl(viewModel.state.url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebViewForTopUpCardTransaction() {
        wb.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                consoleMessage?.message()?.let {
                    if (it.contains("yap.co")){

                    }
                       // setData()
                        //finish()

                }
                return super.onConsoleMessage(consoleMessage)
            }
        }
        wb.settings.javaScriptEnabled = true
        wb.settings.setSupportZoom(true)
        //        wb.loadUrl(viewModel.state.url)
        val base64 =
            android.util.Base64.encodeToString(
                viewModel.state.url.toByteArray(),
                android.util.Base64.NO_PADDING
            )
        wb.loadData(base64, "text/html", "base64")
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
