package co.yap.modules.setcardpin.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.setcardpin.interfaces.ISetCardPin
import co.yap.modules.setcardpin.viewmodels.SetCardPinViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R

class SetCardPinFragment : BaseBindingFragment<ISetCardPin.ViewModel>(), ISetCardPin.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_set_card_pin

    override val viewModel: ISetCardPin.ViewModel
        get() = ViewModelProviders.of(this).get(SetCardPinViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> showToast("Next")
            }
        })
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}