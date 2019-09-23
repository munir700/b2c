package co.yap.modules.dashboard.cards.reportcard.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.reportcard.interfaces.IRepostOrStolenCard
import co.yap.modules.dashboard.cards.reportcard.states.ReportOrStolenCardState
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

class ReportLostOrStolenCardViewModels(application: Application) :
    ReportLostOrStolenCardChildViewModels<IRepostOrStolenCard.State>(application),
    IRepostOrStolenCard.ViewModel {

  override var HOT_LIST_REASON: Int= 2
    val REASON_DAMAGE: Int= 2
    val REASON_LOST_STOLEN: Int= 4

    override val CARD_REORDER_SUCCESS: Int = 5000
    override val cardFee: String = "50"

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val state: ReportOrStolenCardState = ReportOrStolenCardState()

    override fun onResume() {
        super.onResume()
        toggleToolBarVisibility(true)
        setToolBarTitle(getString(Strings.screen_report_card_display_text_title))

    }

    override fun handlePressOnDamagedCard(id: Int) {
        HOT_LIST_REASON= REASON_DAMAGE
        clickEvent.setValue(id)

    }

    override fun handlePressOnLostOrStolen(id: Int) {
        HOT_LIST_REASON= REASON_LOST_STOLEN
        clickEvent.setValue(id)

    }

    override fun handlePressOnReportAndBlockButton(id: Int) {
        clickEvent.setValue(id)
    }


    override fun handlePressOnBackButton() {
        backButtonPressEvent.value = true
    }

    override fun requestConfirmBlockCard() {
        toggleToolBarVisibility(false)
        clickEvent.setValue(CARD_REORDER_SUCCESS)

    }
}