package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.paymentsuccessful

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentPaymentSuccessfulBinding
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseFragment
import co.yap.yapcore.managers.SessionManager

class PaymentSuccessfulFragment:AddMoneyBaseFragment<FragmentPaymentSuccessfulBinding,IPaymentSuccessful.ViewModel>(),
    IPaymentSuccessful.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_payment_successful

    override val viewModel: PaymentSuccessfulViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = this
        setObservers()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, onClickObserver)
        SessionManager.cardBalance.observe(viewLifecycleOwner) { value ->
            viewModel.setNewBalanceData(value.availableBalance.toString())
        }
    }

    private val onClickObserver = Observer<Int> {
        when (it) {
            R.id.btnGoToDashboard -> {
//                startActivity(Intent(requireContext(), YapDashboardActivity::class.java))
//                activity?.finish()
            }
        }
    }

    override fun removeObservers() {
        SessionManager.cardBalance.removeObservers(this)
    }

}