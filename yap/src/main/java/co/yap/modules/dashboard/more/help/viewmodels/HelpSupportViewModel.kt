package co.yap.modules.dashboard.more.help.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.more.help.interfaces.IHelpSupport
import co.yap.modules.dashboard.more.help.states.HelpSupportState
import co.yap.modules.dashboard.more.viewmodels.MoreBaseViewModel
import co.yap.yapcore.SingleClickEvent

class HelpSupportViewModel(application: Application) :
    MoreBaseViewModel<IHelpSupport.State>(application), IHelpSupport.ViewModel/*,
    IRepositoryHolder<CustomersRepository>*/ {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: HelpSupportState = HelpSupportState()

    init {
        state.title.set(getString(R.string.screen_help_support_display_text_title))
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}