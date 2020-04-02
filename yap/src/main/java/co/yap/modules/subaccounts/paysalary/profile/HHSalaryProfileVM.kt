package co.yap.modules.subaccounts.paysalary.profile


import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.R
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import javax.inject.Inject
class HHSalaryProfileVM @Inject constructor(override val state: IHHSalaryProfile.State) :
    BaseRecyclerAdapterVM<PaySalaryModel, IHHSalaryProfile.State>(), IHHSalaryProfile.ViewModel {

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {

    }

    public fun setUpData(arr: ArrayList<PaySalaryModel>){
        addData(arr)
    }

}