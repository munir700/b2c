package co.yap.modules.subaccounts.paysalary.profile

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import javax.inject.Inject

class HHSalaryProfileVM @Inject constructor(override val state: IHHSalaryProfile.State) :
    BaseRecyclerAdapterVM<PaySalaryModel, IHHSalaryProfile.State>(), IHHSalaryProfile.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {

    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let { state.subAccount.value = it.getParcelable(SubAccount::class.simpleName) }

    }

    override fun handlePressOnClick(id: Int) {
        clickEvent.setValue(id)
    }

}