package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.activities.BeneficiaryCashTransferActivity
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IInternationalTransactionConfirmation
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.InternationalTransactionConfirmationViewModel
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingFragment

class InternationalTransactionConfirmationFragment :
    BaseBindingFragment<IInternationalTransactionConfirmation.ViewModel>(),
    IInternationalTransactionConfirmation.View {
    val args: InternationalTransactionConfirmationFragmentArgs by navArgs()
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_international_transaction_confirmation

    override val viewModel: IInternationalTransactionConfirmation.ViewModel
        get() = ViewModelProviders.of(this).get(InternationalTransactionConfirmationViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {
        if (activity is BeneficiaryCashTransferActivity) {
            setData()
            (activity as BeneficiaryCashTransferActivity).viewModel.state.toolBarVisibility = false

        }
    }

    override fun setData() {
        viewModel.state.name = args.name
        viewModel.state.confirmHeading =
            getString(Strings.screen_cash_pickup_funds_display_otp_header)
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.confirmButton -> {
                findNavController().navigate(R.id.action_internationalTransactionConfirmationFragment_to_transferSuccessFragment2)
            }
        }
    }

    override fun onResume() {
        setObservers()
        super.onResume()
    }

    override fun onBackPressed(): Boolean {
        return true
    }

}