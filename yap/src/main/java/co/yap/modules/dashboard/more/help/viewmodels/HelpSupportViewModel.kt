package co.yap.modules.dashboard.more.help.viewmodels

import android.app.Application
import androidx.core.content.ContextCompat
import co.yap.R
import co.yap.modules.dashboard.more.help.interfaces.IHelpSupport
import co.yap.modules.dashboard.more.help.states.HelpSupportState
import co.yap.modules.dashboard.more.home.models.MoreOption
import co.yap.modules.dashboard.more.viewmodels.MoreBaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants

class HelpSupportViewModel(application: Application) :
    MoreBaseViewModel<IHelpSupport.State>(application), IHelpSupport.ViewModel/*,
    IRepositoryHolder<CustomersRepository>*/ {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: HelpSupportState = HelpSupportState()

    init {
        state.title.set("")
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getMoreOptions(): MutableList<MoreOption> {
        val list = mutableListOf<MoreOption>()
        list.add(
            MoreOption(
                Constants.MORE_NOTIFICATION,
                "Notifications",
                R.drawable.ic_notification_more,
                ContextCompat.getColor(context, R.color.colorSecondaryOrange),
                true,
                4
            )
        )
        //colorSecondaryGreen
        list.add(
            MoreOption(
                Constants.MORE_LOCATE_ATM,
                "Locate ATM and CDM",
                R.drawable.ic_home_more,
                ContextCompat.getColor(context, R.color.colorSecondaryGreen),
                false,
                0
            )
        )
        list.add(
            MoreOption(
                Constants.MORE_INVITE_FRIEND,
                "Invite a friend",
                R.drawable.ic_gift,
                ContextCompat.getColor(context, R.color.colorPrimaryAlt),
                false,
                0
            )
        )
        list.add(
            MoreOption(
                Constants.MORE_HELP_SUPPORT,
                "Help and support",
                R.drawable.ic_support,
                ContextCompat.getColor(context, R.color.colorSecondaryBlue),
                false,
                0
            )
        )
        return list
    }
}