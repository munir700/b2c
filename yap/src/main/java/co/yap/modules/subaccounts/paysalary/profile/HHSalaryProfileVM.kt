package co.yap.modules.subaccounts.paysalary.profile

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import javax.inject.Inject
class HHSalaryProfileVM @Inject constructor(override val state: IHHSalaryProfile.State) :
    BaseRecyclerAdapterVM<PaySalaryModel, IHHSalaryProfile.State>(), IHHSalaryProfile.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {

    }

    public fun setUpData(arr: ArrayList<PaySalaryModel>, type: Int){
        if(type == 1){
            addData(arr)
        }else{

        }
    override fun handlePressOnClick(id: Int) {
        clickEvent.setValue(id)
    }

    public fun setUpData(arr: ArrayList<PaySalaryModel>) {
        addData(arr)
    }

}