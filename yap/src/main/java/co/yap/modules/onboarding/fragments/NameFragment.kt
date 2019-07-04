package co.yap.modules.onboarding.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.IName
import co.yap.modules.onboarding.viewmodels.NameViewModel
import co.yap.yapcore.BaseBindingFragment

class NameFragment : BaseBindingFragment<IName.ViewModel>(), IName.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_name

    override val viewModel: IName.ViewModel
        get() = ViewModelProviders.of(this).get(NameViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.nextButtonPressEvent.observe(this, nextButtonObserver)
    }

    private val nextButtonObserver = Observer<Boolean> { navigate(R.id.phoneVerificationFragment) }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.nextButtonPressEvent.removeObserver(nextButtonObserver)
    }
}