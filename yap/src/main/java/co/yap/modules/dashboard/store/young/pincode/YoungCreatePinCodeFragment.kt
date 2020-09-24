package co.yap.modules.dashboard.store.young.pincode

import android.os.Bundle
import co.yap.databinding.FragmentYoungCreatePincodeBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.BR
import co.yap.R
import kotlinx.android.synthetic.main.fragment_young_create_pincode.*

class YoungCreatePinCodeFragment :
    BaseNavViewModelFragment<FragmentYoungCreatePincodeBinding, IYoungPinCode.State, YoungCreatePinCodeVM>() {
    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_young_create_pincode
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        youngDialer.hideFingerprintView()
        youngDialer.upDatedDialerPad(viewModel.state.passCode.value.toString())
    }
    override fun onClick(id: Int) {
    }
}