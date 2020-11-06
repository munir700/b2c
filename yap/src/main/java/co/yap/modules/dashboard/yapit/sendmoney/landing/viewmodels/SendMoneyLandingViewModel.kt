package co.yap.modules.dashboard.yapit.sendmoney.landing.viewmodels

import android.app.Application
import co.yap.R
import co.yap.countryutils.country.utils.CurrencyUtils
import co.yap.modules.dashboard.yapit.sendmoney.landing.ISendMoneyLanding
import co.yap.modules.dashboard.yapit.sendmoney.landing.SendMoneyLandingAdapter
import co.yap.modules.dashboard.yapit.sendmoney.landing.states.SendMoneyLandingState
import co.yap.modules.dashboard.yapit.sendmoney.main.SendMoneyBaseViewMode
import co.yap.modules.dashboard.yapit.sendmoney.main.SendMoneyLandingOptions
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class SendMoneyLandingViewModel(application: Application) :
    SendMoneyBaseViewMode<ISendMoneyLanding.State>(application),
    ISendMoneyLanding.ViewModel {
    override val state: SendMoneyLandingState = SendMoneyLandingState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var landingAdapter: SendMoneyLandingAdapter = SendMoneyLandingAdapter(
        context,
        mutableListOf()
    )

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        setToolBarTitle(getString(Strings.common_send_money))
        landingAdapter.setList(geSendMoneyOptions())
    }

    override fun geSendMoneyOptions(): MutableList<SendMoneyLandingOptions> {
        val list = mutableListOf<SendMoneyLandingOptions>()
        list.add(
            SendMoneyLandingOptions(
                getString(Strings.screen_y2y_display_button_yap_contacts),
                R.drawable.ic_iconprofile,
                false,
                null
            )
        )
        list.add(
            SendMoneyLandingOptions(
                getString(Strings.screen_send_money_local_bank_label),
                R.drawable.ic_banknew,
                true,
                CurrencyUtils.getFlagDrawable(context, "AE")
            )
        )
        list.add(
            SendMoneyLandingOptions(
                getString(Strings.screen_send_money_international_label),
                R.drawable.ic_banknew,
                true,
                CurrencyUtils.getFlagDrawable(context, "AE")
            )
        )
        list.add(
            SendMoneyLandingOptions(
                getString(Strings.screen_send_money_home_label),
                R.drawable.ic_housenew,
                false,
                null
            )
        )
        list.add(
            SendMoneyLandingOptions(
                getString(Strings.screen_fragment_yap_it_add_money_text_qr_code),
                R.drawable.ic_qr_code,
                false,
                null
            )
        )
        return list
    }
}
