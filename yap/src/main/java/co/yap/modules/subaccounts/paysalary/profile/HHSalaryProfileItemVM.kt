package co.yap.modules.subaccounts.paysalary.profile

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.networking.models.ApiResponse
import co.yap.yapcore.BaseListItemViewModel

class HHSalaryProfileItemVM : BaseListItemViewModel<PaySalaryModel>() {
    private lateinit var mItem: PaySalaryModel
    override fun setItem(item: PaySalaryModel, position: Int) {
        mItem = item
    }

    override fun getItem() = mItem

    override fun layoutRes() = R.layout.item_hhsalary_profile_pay_salary

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {}

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }
}