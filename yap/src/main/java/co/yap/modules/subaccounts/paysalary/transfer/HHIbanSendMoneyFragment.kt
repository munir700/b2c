package co.yap.modules.subaccounts.paysalary.transfer

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhibanSendMoneyBinding
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.transactions.household.requestdtos.IbanSendMoneyRequest

import co.yap.yapcore.helpers.extentions.plus
import co.yap.yapcore.helpers.livedata.GetAccountBalanceLiveData
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_hhiban_send_money.*

@AndroidEntryPoint
class HHIbanSendMoneyFragment :
    BaseNavViewModelFragmentV2<FragmentHhibanSendMoneyBinding, IHHIbanSendMoney.State, HHIbanSendMoneyVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hhiban_send_money
    override val viewModel: HHIbanSendMoneyVM by viewModels()
    override fun getToolBarTitle() = viewModel.state.subAccount.value?.getFullName()
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        GetAccountBalanceLiveData.get().observe(this, Observer { response ->
            viewModel.state.availableBalance?.value = response?.availableBalance
        })
        selectMultiCheckGroup?.setOnItemSelectedListener { _, position, isChecked ->
            viewModel.state.selectedTxnCategoryPosition.value = position
        }
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnConfirm -> {
                if (viewModel.state.isRecurringPayment?.value == true && viewModel.state.selectedTxnCategoryPosition.value == 0) {
                    viewModel.getSchedulePayment(viewModel.state.subAccount.value?.accountUuid) {
                        navigateForward(
                            HHIbanSendMoneyFragmentDirections.toRecurringPaymentFragment(),
                            arguments?.plus(
                                bundleOf(SchedulePayment::class.java.simpleName to it)
                            )
                        )
                    }
                } else {
                    val array =
                        resources.getStringArray(R.array.screen_house_hold_iban_send_money_type_array)
                    val request = IbanSendMoneyRequest(
                        viewModel.state.amount?.value,
                        viewModel.state.subAccount.value?.getFullName(),
                        viewModel.state.subAccount.value?.accountUuid,
                        "",
                        array[viewModel.state.selectedTxnCategoryPosition.value ?: 0], "SendMoney"
                    )
                    viewModel.ibanSendMoney(request) {
                        if (it)
                            navigateForward(
                                HHIbanSendMoneyFragmentDirections.actionHHIbanSendMoneyFragmentToHHIbanSendMoneyConfirmationFragment(),
                                arguments?.plus(bundleOf(IbanSendMoneyRequest::class.java.simpleName to request))
                            )
                    }
                }
            }
        }
    }
}


//TODO  Add this line in onSuccess of expense transfer API call. trackEvent(HHUserActivityEvents.HH_EXPENSE_TRANSFERRED.type)