package co.yap.modules.dashboard.cards.addpaymentcard.spare.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.addpaymentcard.spare.interfaces.IAddSpareCard
import co.yap.modules.dashboard.cards.addpaymentcard.spare.states.AddSpareCardState
import co.yap.modules.dashboard.cards.addpaymentcard.viewmodels.AddPaymentChildViewModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent


class AddSpareCardViewModel(application: Application) :
    AddPaymentChildViewModel<IAddSpareCard.State>(application), IAddSpareCard.ViewModel {


    override var cardType: String = ""

    override val clickEvent: SingleClickEvent = SingleClickEvent()
//    override val clickEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val state: AddSpareCardState =
        AddSpareCardState()

    override fun handlePressOnAddVirtualCardSuccess(id:Int) {
        clickEvent.setValue(id)

//        clickEvent.value = true

    }

    override fun handlePressOnAddPhysicalCardSuccess(id:Int) {
        clickEvent.setValue(id)
//        clickEvent.value = true
    }

    override fun handlePressOnConfirmVirtualCardPurchase(id:Int) {
        clickEvent.setValue(id)
//        clickEvent.value = true
        // now remove old include layout_add_spare_virtualcard_confirm_purchase : hide llConfirmVirtualCardPurchase
        //then animate IN new layout layout_add_spare_virtual_card_success  : show layoutVirtualCardOnSuccess

    }

    override fun handlePressOnConfirmPhysicalCardPurchase(id:Int) {
        clickEvent.setValue(id)
//        clickEvent.value = true
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_spare_card_benefit_display_text_title))
    }

}