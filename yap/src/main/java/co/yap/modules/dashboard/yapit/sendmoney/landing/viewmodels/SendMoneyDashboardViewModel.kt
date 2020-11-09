package co.yap.modules.dashboard.yapit.sendmoney.landing.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.countryutils.country.utils.CurrencyUtils
import co.yap.modules.dashboard.yapit.sendmoney.landing.SendMoneyDashboardAdapter
import co.yap.modules.dashboard.yapit.sendmoney.main.ISendMoneyDashboard
import co.yap.modules.dashboard.yapit.sendmoney.main.SendMoneyDashboardState
import co.yap.modules.dashboard.yapit.sendmoney.main.SendMoneyOptions
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class SendMoneyDashboardViewModel(application: Application) :
    BaseViewModel<ISendMoneyDashboard.State>(application),
    ISendMoneyDashboard.ViewModel {
    override val state: SendMoneyDashboardState = SendMoneyDashboardState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var recentTransfers: MutableLiveData<Beneficiary> = MutableLiveData()
    override var dashboardAdapter: SendMoneyDashboardAdapter = SendMoneyDashboardAdapter(
        context,
        mutableListOf()
    )

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        state.toolbarTitle = getString(Strings.common_send_money)
        dashboardAdapter.setList(geSendMoneyOptions())
        toggleRightIconVisibility(true)
        landingAdapter.setList(geSendMoneyOptions())
    }

    override fun geSendMoneyOptions(): MutableList<SendMoneyOptions> {
        val list = mutableListOf<SendMoneyOptions>()
        list.add(
            SendMoneyOptions(
                getString(Strings.screen_y2y_display_button_yap_contacts),
                R.drawable.ic_iconprofile,
                false,
                null
            )
        )
        list.add(
            SendMoneyOptions(
                getString(Strings.screen_send_money_local_bank_label),
                R.drawable.ic_bankicon,
                true,
                CurrencyUtils.getFlagDrawable(context, "AE")
            )
        )
        list.add(
            SendMoneyOptions(
                getString(Strings.screen_send_money_international_label),
                R.drawable.ic_bankicon,
                true,
                CurrencyUtils.getFlagDrawable(context, "AE")
            )
        )
        list.add(
            SendMoneyOptions(
                getString(Strings.screen_send_money_home_label),
                R.drawable.ic_houseicon,
                false,
                null
            )
        )
        list.add(
            SendMoneyOptions(
                getString(Strings.screen_fragment_yap_it_add_money_text_qr_code),
                R.drawable.ic_qr_code,
                false,
                null
            )
        )
        return list
    }
}
