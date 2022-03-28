package co.yap.modules.dashboard.yapit.addmoney.landing

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseViewModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants

class AddMoneyLandingViewModel(application: Application) :
    AddMoneyBaseViewModel<IAddMoneyLanding.State>(application),
    IAddMoneyLanding.ViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: IAddMoneyLanding.State =
        AddMoneyLandingState()
    override val landingAdapter =
        AddMoneyLinearDashboardAdapter(
            mutableListOf(),
            null
        )

    override fun onCreate() {
        super.onCreate()
        setToolBarTitle(getString(Strings.screen_fragment_yap_it_add_money_title))
        landingAdapter.setData(getAddMoneyOptions())
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getAddMoneyOptions(): MutableList<AddMoneyLandingOptions> {
        val list = mutableListOf<AddMoneyLandingOptions>()
        list.add(
            AddMoneyLandingOptions(
                id = Constants.ADD_MONEY_INSTANT_BANK_TRANSFER,
                name = getString(Strings.screen_fragment_yap_it_add_money_instant_bank_transfer_title),
                image = R.drawable.ic_instant_bank_transfer,
                isPadding = true,
                description = getString(Strings.screen_fragment_yap_it_add_money_instant_bank_transfer_description)
            )
        )
        list.add(
            AddMoneyLandingOptions(
                id = Constants.ADD_MONEY_BANK_TRANSFER,
                name = getString(Strings.screen_fragment_yap_it_add_money_text_bank_transfer),
                image = R.drawable.ic_bank_transfer,
                isPadding = true,
                description = getString(Strings.screen_fragment_yap_it_add_money_text_bank_transfer_description)
            )
        )
        list.add(
            AddMoneyLandingOptions(
                Constants.ADD_MONEY_QR_CODE,
                getString(Strings.screen_fragment_yap_it_add_money_text_qr_code),
                R.drawable.ic_qr_code,
                description = getString(Strings.screen_fragment_yap_it_add_money_text_qr_code_description)
            )
        )
        list.add(
            AddMoneyLandingOptions(
                id = Constants.ADD_MONEY_TOP_UP_VIA_CARD,
                name = getString(Strings.screen_fragment_yap_it_add_money_text_top_via_card),
                image = R.drawable.ic_credit_debit,
                isPadding = true,
                description = getString(Strings.screen_fragment_yap_it_add_money_text_top_via_card_description)
            )
        )
        list.add(
            AddMoneyLandingOptions(
                Constants.ADD_MONEY_CASH_OR_CHEQUE,
                getString(Strings.screen_fragment_yap_it_add_money_text_cash_or_cheque),
                R.drawable.ic_cash,
                description = getString(Strings.screen_fragment_yap_it_add_money_text_cash_or_cheque_description)
            )
        )
        /*  list.add(
              AddMoneyLandingOptions(
                  Constants.ADD_MONEY_GOOGLE_PAY,
                  getString(Strings.screen_fragment_yap_it_add_money_text_google_pay),
                  R.drawable.flag_ad
              )
          )
          list.add(
              AddMoneyLandingOptions(
                  Constants.ADD_MONEY_SAMSUNG_PAY,
                  getString(Strings.screen_fragment_yap_it_add_money_text_samsung_pay),
                  R.drawable.flag_ae
              )
          )*/
        return list
    }
}