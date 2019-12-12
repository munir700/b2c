package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.activities.BeneficiaryCashTransferActivity
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ITransferSuccess
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.TransferSuccessViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment

class TransferSuccessFragment : SendMoneyBaseFragment<ITransferSuccess.ViewModel>(),
    ITransferSuccess.View {
    val args: TransferSuccessFragmentArgs by navArgs()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_transfer_success

    override val viewModel: ITransferSuccess.ViewModel
        get() = ViewModelProviders.of(this).get(TransferSuccessViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is BeneficiaryCashTransferActivity) {
            (activity as BeneficiaryCashTransferActivity).let {
                it.viewModel.state.leftButtonVisibility =
                    false
                it.viewModel.state.rightButtonVisibility =
                    false
                it.viewModel.state.toolBarTitle="Cash transfer successful"
            }

        }
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.confirmButton -> {
                    // go back to dashboard
//                    activity!!.recreate()
                    activity?.finish()
//                    findNavController().navigate(R.id.action_beneficiaryOverviewFragment_to_transferSuccessFragment)
                }
            }
        })
    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed(): Boolean {
        return true
    }

}