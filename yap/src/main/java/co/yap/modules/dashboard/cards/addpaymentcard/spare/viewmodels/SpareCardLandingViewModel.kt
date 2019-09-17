package co.yap.modules.dashboard.cards.addpaymentcard.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.addpaymentcard.interfaces.ICards
import co.yap.modules.dashboard.cards.addpaymentcard.states.SpareCardLandingState
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent


class SpareCardLandingViewModel(application: Application) :
    AddPaymentChildViewModel<ICards.State>(application), ICards.ViewModel/*,
    IRepositoryHolder<CustomersRepository>*/ {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: SpareCardLandingState =
        SpareCardLandingState()


    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_spare_card_landing_display_text_title))
    }


}