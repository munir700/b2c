package co.yap.modules.dashboard.yapit.topup.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.dashboard.models.CardInfo
import co.yap.modules.dashboard.yapit.topup.main.carddetail.TopupCardDetailActivity
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.helpers.Utils
import com.google.android.material.appbar.AppBarLayout
import com.thefinestartist.finestwebview.FinestWebView




class TopUpLandingActivity : BaseBindingActivity<ITopUpLanding.ViewModel>() {
    var alias: String? = null
    var cardColor: String? = null
    var sessionId: String? = null

    override fun onNewIntent(intent: Intent?) {
        // yap-app://load?sessionID=SESSION0002571148502K5256417G27color=#00b9aealias=somthing-->
        val uri = intent?.data

        if (uri != null) {
            sessionId = uri.getQueryParameter("sessionID")
            cardColor = uri.getQueryParameter("color")
            alias = uri.getQueryParameter("alias")
        }
        super.onNewIntent(intent)
    }
    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, TopUpLandingActivity::class.java)
        }
    }


    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_topup_landing
    override val viewModel: ITopUpLanding.ViewModel
        get() = ViewModelProviders.of(this).get(
            TopUpLandingViewModel::class.java
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        if (Intent.ACTION_VIEW == intent.action) {
            onNewIntent(intent)
        }
        viewModel.clickEvent.observe(this, clickEventObserver)
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.llBankTransferType -> {
                val card = CardInfo(
                    "7",
                    "#112233",
                    "Citi Bank Card",
                    "1234 5678 8765 7897",
                    "12/2021",
                    "VISA"
                )
                startActivity(
                    TopupCardDetailActivity.getIntent(
                        this@TopUpLandingActivity,
                        card
                    )
                )
                //startActivity(BankDetailActivity.newIntent(this))
            }
            R.id.llCardsTransferType -> {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://dev.yap.co/admin-web/HostedSessionIntegration.html")
                )
                startActivity(browserIntent)
//                openAddTopUpCardPage("https://dev.yap.co/admin-web/HostedSessionIntegration.html")
//                startActivity(TopUpCardsActivity.newIntent(this))
            }
        }
    }

    private fun openAddTopUpCardPage(url: String) {
        FinestWebView.Builder(this)
            .updateTitleFromHtml(true)
            .toolbarScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
            .gradientDivider(false)
            .dividerHeight(2)
            .titleColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
            .toolbarColorRes(R.color.colorWhite)
            .dividerColorRes(R.color.colorPrimaryDark)
            .iconDefaultColorRes(R.color.colorPrimary)
            .iconDisabledColorRes(R.color.light_grey)
            .iconPressedColorRes(R.color.colorPrimaryDark)
            .progressBarHeight(Utils.convertDpToPx(this, 3f))
            .progressBarColorRes(R.color.colorPrimaryDark)
            .backPressToClose(false)
            .webViewUseWideViewPort(true)
            .webViewSupportZoom(true)
            .webViewBuiltInZoomControls(true)
            .webViewAppCacheEnabled(false)
            .setCustomAnimations(
                R.anim.activity_open_enter,
                R.anim.activity_open_exit,
                R.anim.activity_close_enter,
                R.anim.activity_close_exit
            )
            .show(url)

    }


}