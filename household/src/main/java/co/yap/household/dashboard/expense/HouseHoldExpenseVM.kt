package co.yap.household.dashboard.expense

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HouseHoldExpenseVM @Inject constructor(
    override var state: HouseHoldExpenseState
) : HiltBaseViewModel<IHouseHoldExpense.State>(), IHouseHoldExpense.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {}
    override fun handleOnClick(id: Int) {
    }
}