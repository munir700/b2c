package co.yap.modules.dashboard.yapit.sendmoney.homecountry

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.sendmoney.home.activities.SendMoneyLandingActivity
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.enums.SendMoneyTransferType
import co.yap.yapcore.helpers.extentions.launchActivity
import kotlinx.android.synthetic.main.fragment_send_money_home.*

class SMHomeCountryActivity : BaseBindingActivity<ISMHomeCountry.ViewModel>(), ISMHomeCountry.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_send_money_home

    override val viewModel: ISMHomeCountry.ViewModel
        get() = ViewModelProviders.of(this).get(SMHomeCountryViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
        setupRecycler()
    }

    private fun setupRecycler() {
        recyclerViewRecents.adapter = viewModel.recentsAdapter
    }

    private fun addObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnSendMoney -> {
                    startSendMoneyFlow()
                }
                R.id.tvChangeHomeCountry -> {
                    showToast("Change Home Country")
                }
            }
        })
    }

    private fun startSendMoneyFlow() {
        launchActivity<SendMoneyLandingActivity> {
            putExtra(
                SendMoneyLandingActivity.TransferType,
                SendMoneyTransferType.HOME_COUNTRY.name
            )
            putExtra(SendMoneyLandingActivity.searching, false)
        }
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                finish()
            }
        }
    }
}