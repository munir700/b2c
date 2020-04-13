package co.yap.modules.subaccounts.paysalary.profile

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.R
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import com.arthurivanets.bottomsheets.sheets.model.Option
import javax.inject.Inject

class HHSalaryProfileVM @Inject constructor(override val state: IHHSalaryProfile.State) :
    BaseRecyclerAdapterVM<PaySalaryModel, IHHSalaryProfile.State>(), IHHSalaryProfile.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
//        setData(getNoTransactionsData())
    }

//    private fun getNoTransactionsData(): ArrayList<PaySalaryModel> {
//        return ArrayList<PaySalaryModel>().apply {
//            add(
//                PaySalaryModel(
//                    Strings.screen_house_hold_salary_profile_set_up_salary_text,
//                    R.drawable.ic_transaction_rate_arrow
//                )
//            )
//            add(
//                PaySalaryModel(
//                    Strings.screen_house_hold_salary_profile_set_up_expense_text,
//                    R.drawable.ic_expense
//                )
//            )
//            add(
//                PaySalaryModel(
//                    Strings.screen_house_hold_salary_profile_transfer_bonus_text,
//                    R.drawable.ic_yap_to_yap
//                )
//            )
//
//        }
//    }

    override fun handlePressOnClick(id: Int) {
        clickEvent.setValue(id)
    }

}