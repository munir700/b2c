package co.yap.modules.dashboard.cards.reportcard.viewmodels

import android.app.Application
import androidx.appcompat.app.AlertDialog
import co.yap.modules.dashboard.cards.reportcard.interfaces.IRepostOrStolenCard
import co.yap.modules.dashboard.cards.reportcard.states.ReportOrStolenCardState
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

class ReportLostOrStolenCardViewModels(application: Application) :
    ReportLostOrStolenCardChildViewModels<IRepostOrStolenCard.State>(application),
    IRepostOrStolenCard.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val state: ReportOrStolenCardState = ReportOrStolenCardState()

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_report_card_display_text_title))

    }

    override fun handlePressOnDamagedCard(id: Int) {
        clickEvent.setValue(id)

     }

    override fun handlePressOnLostOrStolen(id: Int) {
        clickEvent.setValue(id)

    }

    override fun handlePressOnReportAndBlockButton(id: Int) {
        clickEvent.setValue(id)
    }


    override fun handlePressOnBackButton() {
        backButtonPressEvent.value = true
    }

}