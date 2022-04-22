package co.yap.modules.dashboard.more.home.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.home.interfaces.IInviteFriend
import co.yap.modules.dashboard.more.home.states.InviteFriendState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class InviteFriendViewModel(application: Application) :
    BaseViewModel<IInviteFriend.State>(application = application), IInviteFriend.ViewModel {
    override val state: InviteFriendState = InviteFriendState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        setUpStrings()
    }

    override fun handlePressOnButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun setUpStrings() {
//        state.toolbarTitle = getString(Strings.screen_invite_friend_text_title)
    }

}