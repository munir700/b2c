package co.yap.modules.subaccounts.paysalary.employee

import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentPayHhemployeeSalaryBinding
import co.yap.yapcore.dagger.base.BaseViewModelFragment

class PayHHEmployeeSalaryFragment :
    BaseViewModelFragment<FragmentPayHhemployeeSalaryBinding, IPayHHEmployeeSalary.State, PayHHEmployeeSalaryVM>() {
    override fun getBindingVariable() = BR.payHHEmployeeSalaryVM

    override fun getLayoutId() = R.layout.fragment_pay_hhemployee_salary
}