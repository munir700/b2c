package co.yap.modules.kyc.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.IInformationError
import co.yap.modules.kyc.viewmodels.InformationErrorViewModel

class InformationErrorFragment : KYCChildFragment<IInformationError.ViewModel>() {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_information_error
    override val viewModel: IInformationError.ViewModel
        get() = ViewModelProviders.of(this).get(InformationErrorViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.gotoDashboardPressEvent.observe(this, gotoDashboardpressEventObserver)
    }

    private val gotoDashboardpressEventObserver = Observer<Boolean> {
        //startActivity(LiteDashboardFragment.newIntent(this@InformationErrorActivity)
    }
}