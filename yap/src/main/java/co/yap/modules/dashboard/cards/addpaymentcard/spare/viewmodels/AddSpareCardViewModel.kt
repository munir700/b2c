package co.yap.modules.dashboard.cards.addpaymentcard.spare.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.addpaymentcard.spare.helpers.physical.AddSparePhysicalCardLogicHelper
import co.yap.modules.dashboard.cards.addpaymentcard.spare.helpers.virtual.AddSpareVirtualCardLogicHelper
import co.yap.modules.dashboard.cards.addpaymentcard.spare.interfaces.IAddSpareCard
import co.yap.modules.dashboard.cards.addpaymentcard.spare.states.AddSpareCardState
import co.yap.modules.dashboard.cards.addpaymentcard.viewmodels.AddPaymentChildViewModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent


class AddSpareCardViewModel(application: Application) :
    AddPaymentChildViewModel<IAddSpareCard.State>(application), IAddSpareCard.ViewModel {

    override val addSparePhysicalCardLogicHelper: AddSparePhysicalCardLogicHelper =
        AddSparePhysicalCardLogicHelper(context, this)

    override val addSpareVirtualCardLogicHelper: AddSpareVirtualCardLogicHelper =
        AddSpareVirtualCardLogicHelper(context, this)

    override var cardType: String = ""

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: AddSpareCardState =
        AddSpareCardState()

    override fun handlePressOnAddVirtualCardSuccess(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnConfirmPhysicalCardLocation(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnAddPhysicalCardSuccess(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnConfirmVirtualCardPurchase(id: Int) {
        clickEvent.setValue(id)

    }

    override fun handlePressOnConfirmPhysicalCardPurchase(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnConfirmLocation(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnChangeLocation(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_spare_card_landing_display_text_title))
        toggleToolBarVisibility(true)
        state.onChangeLocationClick = false
        toggleToolBarVisibility(true)
    }

    override fun onPause() {
        super.onPause()
        if (state.onChangeLocationClick) {
            toggleToolBarVisibility(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (state.onChangeLocationClick) {
            toggleToolBarVisibility(false)
        }

    }
}