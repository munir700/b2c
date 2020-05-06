package co.yap.modules.subaccounts.paysalary.employee

import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentPayHhemployeeSalaryBinding
import co.yap.yapcore.dagger.base.BaseViewModelFragment
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class PayHHEmployeeSalaryFragment :
    BaseNavViewModelFragment<FragmentPayHhemployeeSalaryBinding, IPayHHEmployeeSalary.State, PayHHEmployeeSalaryVM>() {
    override fun getBindingVariable() = BR.payHHEmployeeSalaryVM

    override fun getLayoutId() = R.layout.fragment_pay_hhemployee_salary

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        //
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    private fun onClick(id: Int) {
        when (id) {
            R.id.btnPayNow -> navigateForwardWithAnimation(
                PayHHEmployeeSalaryFragmentDirections.actionPayHHEmployeeSalaryFragmentToEnterSalaryAmountFragment(),
                arguments
            )
            R.id.llScheduleOnce -> navigateForwardWithAnimation(
                PayHHEmployeeSalaryFragmentDirections.actionPayHHEmployeeSalaryFragmentToFuturePaymentFragment(),
                arguments
            )
            R.id.llMakeRecurring -> navigateForwardWithAnimation(
                PayHHEmployeeSalaryFragmentDirections.actionPayHHEmployeeSalaryFragmentToRecurringPaymentFragment(),
                arguments
            )


        }
    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()
    }
}
