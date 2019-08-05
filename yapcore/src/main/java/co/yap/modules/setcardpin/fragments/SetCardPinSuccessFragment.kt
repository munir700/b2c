package co.yap.modules.setcardpin.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.setcardpin.interfaces.ISetCardPinSuccess
import co.yap.modules.setcardpin.viewmodels.SetCardPinSuccessViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R

class SetCardPinSuccessFragment : BaseBindingFragment<ISetCardPinSuccess.ViewModel>(), ISetCardPinSuccess.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_set_card_pin_success

    override val viewModel: ISetCardPinSuccess.ViewModel
        get() = ViewModelProviders.of(this).get(SetCardPinSuccessViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnTopUp -> showToast("Top Up Now!")
                R.id.tvTopUpLater -> showToast("Top Up later!")
            }
        })
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}