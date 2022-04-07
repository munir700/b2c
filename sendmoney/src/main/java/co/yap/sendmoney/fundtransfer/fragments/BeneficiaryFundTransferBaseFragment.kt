package co.yap.sendmoney.fundtransfer.fragments

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import co.yap.sendmoney.fundtransfer.viewmodels.BeneficiaryFundTransferBaseViewModel
import co.yap.sendmoney.fundtransfer.viewmodels.BeneficiaryFundTransferViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class BeneficiaryFundTransferBaseFragment<VB : ViewDataBinding,V : IBase.ViewModel<*>> :
    BaseBindingFragment<VB,V>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is BeneficiaryFundTransferBaseViewModel<*> && activity != null) {
            (viewModel as BeneficiaryFundTransferBaseViewModel<*>).parentViewModel =
                ViewModelProviders.of(requireActivity())
                    .get(BeneficiaryFundTransferViewModel::class.java)
        }
    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }
}