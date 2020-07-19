package co.yap.household.onboard.onboarding.newuser

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.household.BR
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.BaseState
@Deprecated("")
class NewHouseHoldUserState : BaseState(), INewHouseHoldUser.State {

    override var firstName : MutableLiveData<String> = MutableLiveData()
    override var lastName: MutableLiveData<String> = MutableLiveData()
    override var accountInfo: MutableLiveData<AccountInfo> = MutableLiveData()

}
