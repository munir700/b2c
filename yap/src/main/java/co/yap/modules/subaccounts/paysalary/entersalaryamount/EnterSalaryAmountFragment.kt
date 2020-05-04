package co.yap.modules.subaccounts.paysalary.entersalaryamount

import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentEnterSalaryAmountBinding
import co.yap.yapcore.dagger.base.BaseViewModelFragment
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.livedata.GetAccountBalanceLiveData

class EnterSalaryAmountFragment :
    BaseNavViewModelFragment<FragmentEnterSalaryAmountBinding, IEnterSalaryAmount.State, EnterSalaryAmountVM>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_enter_salary_amount
    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        GetAccountBalanceLiveData.get().observe(this, Observer { response ->

        })
    }

}
