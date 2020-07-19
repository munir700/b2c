package co.yap.household.onboard.onboarding.invalideid

import android.app.Application
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.managers.MyUserManager
@Deprecated("")
class InvalidEIDSuccessViewModel(application: Application) :
    BaseViewModel<IInvalidEIDSuccess.State>(application = application), IInvalidEIDSuccess.ViewModel{
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        state.name = MyUserManager.user?.currentCustomer?.getFullName()
    }

    override fun handlePressOnView(id: Int) {
        MyUserManager.doLogout(context)
        clickEvent.setValue(id)
    }

    override val state = InvalidEIDSuccessState(application)


}