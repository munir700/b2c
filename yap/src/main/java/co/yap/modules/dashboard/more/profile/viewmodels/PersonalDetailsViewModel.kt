package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.profile.intefaces.IPersonalDetail
import co.yap.modules.dashboard.more.profile.states.PersonalDetailState
import co.yap.modules.dashboard.more.viewmodels.MoreBaseViewModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class PersonalDetailsViewModel(application: Application) :
    MoreBaseViewModel<IPersonalDetail.State>(application), IPersonalDetail.ViewModel {
    override fun handlePressOnEditPhone(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnEditEmail(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnEditAddress(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnDocumentCard(id: Int) {
        clickEvent.setValue(id)
    }

    override val state: PersonalDetailState =
        PersonalDetailState()

    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnBackButton() {

    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_personal_detail_display_text_title))
    }
}