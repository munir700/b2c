package co.yap.modules.dashboard.yapit.addmoney.landing

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseViewModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import com.liveperson.infra.utils.Utils.getResources

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
        for (i in 0..4) {
            list.add(
                AddMoneyLandingOptions(
                    id = when (i) {
                        0 -> Constants.ADD_MONEY_INSTANT_BANK_TRANSFER
                        1 -> Constants.ADD_MONEY_BANK_TRANSFER
                        2 -> Constants.ADD_MONEY_QR_CODE
                        3 -> Constants.ADD_MONEY_TOP_UP_VIA_CARD
                        4 -> Constants.ADD_MONEY_CASH_OR_CHEQUE
                        else -> 0
                    },
                    name = getResources().getStringArray(R.array.yap_it_add_money_title)[i],
                    description = getResources().getStringArray(R.array.yap_it_add_money_desc)[i],
                    image = getResources().getStringArray(R.array.yap_it_add_money_drawable)[i]
                )
            )
        }
        return list
    }
}