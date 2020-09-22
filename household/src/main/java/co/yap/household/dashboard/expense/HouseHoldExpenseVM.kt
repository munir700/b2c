package co.yap.household.dashboard.expense

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HouseHoldExpenseVM @Inject constructor(
    override var state: IHouseHoldExpense.State
) : DaggerBaseViewModel<IHouseHoldExpense.State>(), IHouseHoldExpense.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {}
    override fun handleOnClick(id: Int) {
    }
}