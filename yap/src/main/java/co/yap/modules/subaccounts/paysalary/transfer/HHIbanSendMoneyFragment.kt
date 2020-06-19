package co.yap.modules.subaccounts.paysalary.transfer

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhibanSendMoneyBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.livedata.GetAccountBalanceLiveData
import kotlinx.android.synthetic.main.fragment_hhiban_send_money.*

class HHIbanSendMoneyFragment :
    BaseNavViewModelFragment<FragmentHhibanSendMoneyBinding, IHHIbanSendMoney.State, HHIbanSendMoneyVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hhiban_send_money
    override fun getToolBarTitle() = state.subAccount.value?.getFullName()
    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        GetAccountBalanceLiveData.get().observe(this, Observer { response ->
            viewModel.state.availableBalance?.value = response?.availableBalance
        })
        selectMultiCheckGroup?.setOnItemSelectedListener { _, position, isChecked ->
            cbOutTransFilter?.visibility = if (isChecked && position == 0) VISIBLE else GONE
        }
    }
}


//TODO  Add this line in onSuccess of expense transfer API call. trackEvent(HHUserActivityEvents.HH_EXPENSE_TRANSFERRED.type)