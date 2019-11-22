package co.yap.modules.dashboard.yapit.topup.addtopupcard.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.dashboard.yapit.topup.topupamount.activities.TopUpCardActivity
import co.yap.modules.dashboard.yapit.topup.addtopupcard.AddTopUpCardDialog
import co.yap.modules.dashboard.yapit.topup.addtopupcard.interfaces.IAddTopUpCard
import co.yap.modules.dashboard.yapit.topup.addtopupcard.viewmodels.AddTopUpCardViewModel
import co.yap.networking.customers.responsedtos.beneficiary.TopUpCard
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.constants.Constants
import kotlinx.android.synthetic.main.activity_add_top_up_card.*

class AddTopUpCardActivity : BaseBindingActivity<IAddTopUpCard.ViewModel>(), IAddTopUpCard.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_add_top_up_card
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
        if (intent.hasExtra(Constants.KEY) && intent.hasExtra(Constants.TYPE)) {
            viewModel.state.url = intent.getStringExtra(Constants.KEY)
            type = intent.getStringExtra(Constants.TYPE)
            if (type == Constants.TYPE_ADD_CARD) {
                viewModel.state.toolbarVisibility.set(true)
                setupWebViewForAddCard()
            } else if (type == Constants.TYPE_TOP_UP_TRANSACTION) {
                viewModel.state.toolbarVisibility.set(false)
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
                    if (request.url.toString().startsWith("yap-app://")) {
                        onNewIntent(intent)
                        sessionId = request.url.getQueryParameter("sessionID")
                        cardColor = request.url.getQueryParameter("color")
                        alias = request.url.getQueryParameter("alias")
                        // view?.visibility = View.GONE
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
                    if (it.contains("yap.co") || it.contains("transactions")) {
                        setDataForTopUpTransaction(true)
                        finish()
                    }else{

                    }

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
        tbBtnBack.setOnClickListener {
            onBackPressed()
        }
        viewModel.isCardAdded?.observe(this, Observer {
            if (it != null)
                showAddCardDialog(it)
        })

    }

    private fun setData(isCardAdded: Boolean) {
        val intent = Intent()
        intent.putExtra("isCardAdded", isCardAdded)
        setResult(Activity.RESULT_OK, intent)
    }

    private fun showAddCardDialog(card: TopUpCard) {
        AddTopUpCardDialog.newInstance(object : AddTopUpCardDialog.OnProceedListener {
            override fun onProceed(id: Int) {
                when (id) {
                    R.id.done -> {
                        startActivity(TopUpCardActivity.newIntent(this@AddTopUpCardActivity, card))
                        setData(true)
                        finish()
                    }
                    R.id.btnLater -> {
                        setData(true)
                        finish()
                    }
                }
            }

        }, this).show()
    }

    private fun setDataForTopUpTransaction(isStartPooling: Boolean = false) {
        val intent = Intent()
        intent.putExtra(Constants.START_POOLING, isStartPooling)
        setResult(Activity.RESULT_OK, intent)
    }
}
