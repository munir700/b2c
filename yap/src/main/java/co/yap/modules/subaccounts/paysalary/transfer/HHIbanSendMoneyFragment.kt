package co.yap.modules.subaccounts.paysalary.transfer

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhibanSendMoneyBinding
import co.yap.networking.transactions.household.requestdtos.IbanSendMoneyRequest
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.plus
import co.yap.yapcore.helpers.livedata.GetAccountBalanceLiveData
import kotlinx.android.synthetic.main.fragment_hhiban_send_money.*

class HHIbanSendMoneyFragment :
    BaseNavViewModelFragment<FragmentHhibanSendMoneyBinding, IHHIbanSendMoney.State, HHIbanSendMoneyVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hhiban_send_money
    override fun getToolBarTitle() = state.subAccount.value?.getFullName()
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        GetAccountBalanceLiveData.get().observe(this, Observer { response ->
            viewModel.state.availableBalance?.value = response?.availableBalance
        })
        selectMultiCheckGroup?.setOnItemSelectedListener { _, position, isChecked ->
            val array =
                resources.getStringArray(R.array.screen_house_hold_iban_send_money_type_array)
            viewModel.state.txnCategory.value = array[position]
            cbOutTransFilter?.visibility = if (isChecked && position == 0) VISIBLE else GONE
        }
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnConfirm -> {
                val request = IbanSendMoneyRequest(
                    viewModel.state.amount?.value,
                    viewModel.state.subAccount.value?.getFullName(),
                    viewModel.state.subAccount.value?.accountUuid,
                    "",
                    viewModel.state.txnCategory.value, "SendMoney"
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

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}


//TODO  Add this line in onSuccess of expense transfer API call. trackEvent(HHUserActivityEvents.HH_EXPENSE_TRANSFERRED.type)