package co.yap.modules.subaccounts.paysalary.entersalaryamount

import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentEnterSalaryAmountBinding
import co.yap.yapcore.dagger.base.BaseViewModelFragment

class EnterSalaryAmountFragment :
    BaseViewModelFragment<FragmentEnterSalaryAmountBinding, IEnterSalaryAmount.State, EnterSalaryAmountVM>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_enter_salary_amount

}
