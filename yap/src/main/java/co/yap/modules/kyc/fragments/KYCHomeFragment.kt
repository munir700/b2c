package co.yap.modules.kyc.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.kyc.interfaces.IKYCHome
import co.yap.modules.kyc.viewmodels.KYCHomeViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment

class KYCHomeFragment : BaseBindingFragment<IKYCHome.ViewModel>(), IKYCHome.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_kyc_home

    override val viewModel: IKYCHome.ViewModel
        get() = ViewModelProviders.of(this).get(KYCHomeViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.clickListener.observe(this, Observer {
            when (it) {
                R.id.cvCard -> {

                }
                R.id.btnNext -> {

                }
                R.id.tvSkip -> {

                }
            }
        })
    }

    override fun onDestroyView() {
        viewModel.clickListener.removeObservers(this)
        super.onDestroyView()
    }

}