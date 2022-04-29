package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.topupsuccessscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentPaymentSuccessfulBinding
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseFragment
import co.yap.yapcore.managers.SessionManager
import com.yap.core.extensions.finishAffinity

class PaymentSuccessfulFragment :
    AddMoneyBaseFragment<FragmentPaymentSuccessfulBinding, IPaymentSuccessful.ViewModel>(),
    IPaymentSuccessful.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_payment_successful

    override val viewModel: PaymentSuccessfulViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = this
        setObservers()
        viewModel.getAccountBalanceRequest()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, onClickObserver)
    }

    private val onClickObserver = Observer<Int> {
        when (it) {
            R.id.btnGoToDashboard -> {
                startActivity(Intent(requireContext(), YapDashboardActivity::class.java))
                finishAffinity()
            }
        }
    }

    override fun removeObservers() {
        SessionManager.cardBalance.removeObservers(this)
    }

}