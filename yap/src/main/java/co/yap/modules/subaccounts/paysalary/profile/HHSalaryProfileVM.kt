package co.yap.modules.subaccounts.paysalary.profile

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.R
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import com.arthurivanets.bottomsheets.sheets.model.Option
import javax.inject.Inject

class HHSalaryProfileVM @Inject constructor(override val state: IHHSalaryProfile.State) :
    BaseRecyclerAdapterVM<PaySalaryModel, IHHSalaryProfile.State>(), IHHSalaryProfile.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {

    }

    override fun fetchExtras(bundle: Bundle?) {
        super.fetchExtras(bundle)
        bundle?.let { state.subAccount.value = it.getParcelable(SubAccount::class.simpleName) }

    }

    override fun handlePressOnClick(id: Int) {
        clickEvent.setValue(id)
    }

}