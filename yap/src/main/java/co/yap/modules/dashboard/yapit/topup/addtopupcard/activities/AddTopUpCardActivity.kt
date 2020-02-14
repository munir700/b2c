package co.yap.modules.dashboard.yapit.topup.addtopupcard.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
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
    var errors: String? = null
    var alias: String? = null
    var cardColor: String? = null
    var sessionId: String? = null
    var cardNumber: String? = null
    var type: String? = null

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
                        errors = request.url.getQueryParameter("errors")
                        if (errors == null) {
                            sessionId = request.url.getQueryParameter("sessionID")
                            cardColor = request.url.getQueryParameter("color")
                            alias = request.url.getQueryParameter("alias")
                            cardNumber = request.url.getQueryParameter("number")
                            viewModel.addTopUpCard(
                                sessionId.toString(),
                                alias.toString(),
                                cardColor.toString(),
                                cardNumber.toString()
                            )
                        } else {
                            showToast(errors.toString())
                            recreate()
                        }
                        return true
                    }
                }
                return false
            }
        }
        wb.settings.javaScriptEnabled = true
//        wb.clearCache(true)
        wb.settings.setSupportZoom(true)
        wb.loadUrl(viewModel.state.url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebViewForTopUpCardTransaction() {
        wb.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                url?.let {
                    if (it.contains("yap.co") || it.contains("transactions")) {
                        view?.visibility = View.GONE
                        setDataForTopUpTransaction(true)
                        finish()
                    } else {
                        super.onPageStarted(view, url, favicon)
                    }
                }

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

    private fun setData(card: TopUpCard?) {
        val intent = Intent()
        intent.putExtra("card", card)
        intent.putExtra("isCardAdded", true)
        setResult(Activity.RESULT_OK, intent)
    }

    private fun showAddCardDialog(card: TopUpCard) {
        AddTopUpCardDialog.newInstance(object : AddTopUpCardDialog.OnProceedListener {
            override fun onProceed(id: Int) {
                when (id) {
                    R.id.done -> {
                        setData(card)
                        finish()
                    }
                    R.id.btnLater -> {
                        setData(null)
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
