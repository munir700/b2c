package co.yap.modules.dashboard.cards.addpaymentcard.spare.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.addpaymentcard.spare.interfaces.IAddSpareCard
import co.yap.modules.dashboard.cards.addpaymentcard.spare.states.AddSpareCardState
import co.yap.modules.dashboard.cards.addpaymentcard.viewmodels.AddPaymentChildViewModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent


class AddSpareCardViewModel(application: Application) :
    AddPaymentChildViewModel<IAddSpareCard.State>(application), IAddSpareCard.ViewModel {

    override var cardType: String=""

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: AddSpareCardState =
        AddSpareCardState()

    override fun handlePressOnAddVirtualCardSuccess(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnAddPhysicalCardSuccess(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_spare_card_benefit_display_text_title))
    }

}