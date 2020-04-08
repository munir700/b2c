package co.yap.modules.dashboard.main.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.app.YAPApplication
import co.yap.modules.dashboard.main.interfaces.IYapDashboard
import co.yap.modules.dashboard.main.states.YapDashBoardState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.Utils.formateIbanString
import co.yap.yapcore.managers.MyUserManager
import kotlinx.coroutines.delay

class YapDashBoardViewModel(application: Application) :
    BaseViewModel<IYapDashboard.State>(application), IYapDashboard.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapDashBoardState = YapDashBoardState()
    override val showUnverifedscreen: MutableLiveData<Boolean> = MutableLiveData()

    override fun handlePressOnNavigationItem(id: Int) {
        clickEvent.setValue(id)
    }

    override fun copyAccountInfoToClipboard() {
        val info = "Account: ${state.accountNo}\nIBAN: ${state.ibanNo}"
        Utils.copyToClipboard(context, info)
        state.toast = "Copied to clipboard"
    }

    override fun onCreate() {
        super.onCreate()
        updateVersion()
        launch {
            delay(1500)
            showUnverifedscreen.value =
                MyUserManager.user?.currentCustomer?.isEmailVerified.equals("N", true)
        }
    }

    private fun updateVersion() {
        state.appVersion.set(
            String.format(
                "Version %s (%s)",
                YAPApplication.appInfo?.version_name,
                YAPApplication.appInfo?.version_code
            )
        )
    }

    override fun onResume() {
        super.onResume()
        populateState()
    }

    private fun populateState() {
        MyUserManager.user?.let { it ->
            it.accountNo?.let { state.accountNo = it }
            it.iban?.let { state.ibanNo = formateIbanString(it) ?: "" }
            state.fullName = it.currentCustomer.getFullName()
            state.firstName = it.currentCustomer.firstName
            state.userNameImage.set(it.currentCustomer.getPicture() ?: "")
        }
    }
}