package co.yap.modules.dashboard.yapit.addmoney

import android.app.Application
import androidx.core.content.ContextCompat
import co.yap.R
import co.yap.modules.dashboard.more.home.models.MoreOption
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import com.leanplum.Leanplum

class AddMoneyViewModel (application: Application):
        BaseViewModel<IAddMoney.State>(application), IAddMoney.ViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: IAddMoney.State = AddMoneyState()

    override fun onCreate() {
        super.onCreate()
        state.toolbarTitle = getString(Strings.screen_fragment_yap_it_add_money_title)
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
                R.drawable.ic_icon_card_transfer,
                ContextCompat.getColor(context, R.color.colorPrimary),
                ContextCompat.getColor(context, R.color.colorPrimary)
            )
        )
        //colorSecondaryGreen
        list.add(
            AddMoneyOptions(
                Constants.ADD_MONEY_GOOGLE_PAY,
                getString(Strings.screen_fragment_yap_it_add_money_text_google_pay),
                R.drawable.flag_ad,
                0,
                0
            )
        )
        list.add(
            AddMoneyOptions(
                Constants.ADD_MONEY_SAMSUNG_PAY,
                getString(Strings.screen_fragment_yap_it_add_money_text_samsung_pay),
                R.drawable.flag_ae,
                0,
            0
            )
        )
        list.add(
            AddMoneyOptions(
                Constants.ADD_MONEY_BANK_TRANSFER,
                getString(Strings.screen_fragment_yap_it_add_money_text_bank_transfer),
                R.drawable.ic_bank,
                ContextCompat.getColor(context, R.color.colorPrimary),
                0
            )
        )
        list.add(
            AddMoneyOptions(
                Constants.ADD_MONEY_CASH_OR_CHEQUE,
                getString(Strings.screen_fragment_yap_it_add_money_text_cash_or_cheque),
                R.drawable.ic_cash_out_trasaction,
                ContextCompat.getColor(context, R.color.colorPrimary),
                0
            )
        )
        list.add(
            AddMoneyOptions(
                Constants.ADD_MONEY_QR_CODE,
                getString(Strings.screen_fragment_yap_it_add_money_text_qr_code),
                R.drawable.ic_qr_code,
                ContextCompat.getColor(context, R.color.colorPrimary),
                0
            )
        )
        return list
    }
}