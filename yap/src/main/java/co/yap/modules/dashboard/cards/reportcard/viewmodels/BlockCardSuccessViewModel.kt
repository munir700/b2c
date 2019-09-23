package co.yap.modules.dashboard.cards.reportcard.viewmodels

import android.app.Application
import co.yap.translation.Strings
import co.yap.translation.Strings.screen_spare_card_landing_display_text_title
import co.yap.yapcore.defaults.DefaultState
import co.yap.yapcore.defaults.IDefault

class BlockCardSuccessViewModel(application: Application) :
    ReportLostOrStolenCardChildViewModels<IDefault.State>(application),
    IDefault.ViewModel {
    override val state: IDefault.State
        get() = DefaultState()


    override fun onResume() {
        super.onResume()
        toggleToolBarVisibility(false)
        setToolBarTitle(getString(Strings.screen_report_card_display_text_title))

    }

    override fun onPause() {
        super.onPause()
        toggleToolBarVisibility(true)
        setToolBarTitle(getString(screen_spare_card_landing_display_text_title))
    }

}