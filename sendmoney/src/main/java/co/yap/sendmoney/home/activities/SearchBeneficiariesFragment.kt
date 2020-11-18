package co.yap.sendmoney.home.activities

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.sendmoney.BR
import co.yap.sendmoney.R
import co.yap.sendmoney.databinding.FragmentTransferSuccessBinding
import co.yap.sendmoney.home.interfaces.ISMSearchBeneficiary
import co.yap.sendmoney.home.main.SMBeneficiaryParentBaseFragment
import co.yap.sendmoney.viewmodels.SMSearchBeneficiaryViewModel


class SearchBeneficiariesFragment :
    SMBeneficiaryParentBaseFragment<ISMSearchBeneficiary.ViewModel>(),
    ISMSearchBeneficiary.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_search_beneficiary

    override val viewModel: SMSearchBeneficiaryViewModel
        get() = ViewModelProviders.of(this).get(SMSearchBeneficiaryViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickListener)
    }

    private val clickListener = Observer<Int> {
        when (it) {
        }
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        removeObservers()
    }

    private fun getBindings(): FragmentTransferSuccessBinding {
        return viewDataBinding as FragmentTransferSuccessBinding
    }

}