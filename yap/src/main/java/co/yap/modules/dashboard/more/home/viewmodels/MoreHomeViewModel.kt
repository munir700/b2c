package co.yap.modules.dashboard.more.home.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.more.home.interfaces.IMoreHome
import co.yap.modules.dashboard.more.home.models.MoreOption
import co.yap.modules.dashboard.more.home.states.MoreState
import co.yap.modules.dashboard.more.viewmodels.MoreBaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.managers.MyUserManager

class MoreHomeViewModel(application: Application) :
    MoreBaseViewModel<IMoreHome.State>(application), IMoreHome.ViewModel/*,
    IRepositoryHolder<CustomersRepository>*/ {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: MoreState = MoreState()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getMoreOptions(): MutableList<MoreOption> {
        val list = mutableListOf<MoreOption>()
        list.add(MoreOption(1, "Notifications", R.drawable.ic_bulb, false, 0))
        list.add(MoreOption(2, "Locate ATM and CDM ", R.drawable.ic_bulb, false, 0))
        list.add(MoreOption(3, "Invite a friend", R.drawable.ic_bulb, false, 0))
        list.add(MoreOption(4, "Help and support", R.drawable.ic_bulb, false, 0))
        return list
    }
}