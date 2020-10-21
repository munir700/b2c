package co.yap.modules.dashboard.yapit.addmoney.landing

import android.app.Application
import co.yap.R
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants

class AddMoneyViewModel(application: Application) :
    BaseViewModel<IAddMoney.State>(application),
    IAddMoney.ViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: IAddMoney.State =
        AddMoneyState()
    override val adapter =
        AddMoneyAdapter(
            context,
            mutableListOf()
        )

    override fun onCreate() {
        super.onCreate()
        state.toolbarTitle = getString(Strings.screen_fragment_yap_it_add_money_title)
        adapter.setList(getAddMoneyOptions())
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getAddMoneyOptions(): MutableList<AddMoneyOptions> {
        val list = mutableListOf<AddMoneyOptions>()
        list.add(
            AddMoneyOptions(
                Constants.ADD_MONEY_TOP_UP_VIA_CARD,
                getString(Strings.screen_fragment_yap_it_add_money_text_top_via_card),
                R.drawable.ic_icon_card_transfer
            )
        )
        list.add(
            AddMoneyOptions(
                Constants.ADD_MONEY_GOOGLE_PAY,
                getString(Strings.screen_fragment_yap_it_add_money_text_google_pay),
                R.drawable.flag_ad
            )
        )
        list.add(
            AddMoneyOptions(
                Constants.ADD_MONEY_SAMSUNG_PAY,
                getString(Strings.screen_fragment_yap_it_add_money_text_samsung_pay),
                R.drawable.flag_ae
            )
        )
        list.add(
            AddMoneyOptions(
                Constants.ADD_MONEY_BANK_TRANSFER,
                getString(Strings.screen_fragment_yap_it_add_money_text_bank_transfer),
                R.drawable.ic_bank_transfer
            )
        )
        list.add(
            AddMoneyOptions(
                Constants.ADD_MONEY_CASH_OR_CHEQUE,
                getString(Strings.screen_fragment_yap_it_add_money_text_cash_or_cheque),
                R.drawable.ic_cash
            )
        )
        list.add(
            AddMoneyOptions(
                Constants.ADD_MONEY_QR_CODE,
                getString(Strings.screen_fragment_yap_it_add_money_text_qr_code),
                R.drawable.ic_qr_code
            )
        )
        return list
    }
}