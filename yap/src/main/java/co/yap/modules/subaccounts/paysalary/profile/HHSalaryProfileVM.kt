package co.yap.modules.subaccounts.paysalary.profile

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.R
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import javax.inject.Inject

class HHSalaryProfileVM @Inject constructor(override val state: IHHSalaryProfile.State) :
    BaseRecyclerAdapterVM<PaySalaryModel, IHHSalaryProfile.State>(), IHHSalaryProfile.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        addData(getPaySalaryData())
    }

    fun getPaySalaryData(): ArrayList<PaySalaryModel>{
        var array:ArrayList<PaySalaryModel> = ArrayList()
        array.add(PaySalaryModel("Set up a salary", R.drawable.ic_transaction_rate_arrow))
        array.add(PaySalaryModel("Set up an expense pot", R.drawable.ic_expense))
        array.add(PaySalaryModel("Transfer a bonus or extra cash", R.drawable.ic_yap_to_yap))

        return array
    }
}