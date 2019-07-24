package co.yap.modules.kyc.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.kyc.interfaces.IAddressSelection
import co.yap.modules.kyc.viewmodels.AddressSelectionViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment

class AddressSelectionFragment : BaseBindingFragment<IAddressSelection.ViewModel>(), IAddressSelection.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_address_selection

    override val viewModel: IAddressSelection.ViewModel
        get() = ViewModelProviders.of(this).get(AddressSelectionViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel.clickListener.observe(this, Observer {
//            when (it) {
//                R.id.cvCard -> {
//
//                }
//                R.id.btnNext -> {
//
//                }
//                R.id.tvSkip -> {
//
//                }
//            }
//        })
    }

    override fun onDestroyView() {
//        viewModel.clickListener.removeObservers(this)
        super.onDestroyView()
    }

}