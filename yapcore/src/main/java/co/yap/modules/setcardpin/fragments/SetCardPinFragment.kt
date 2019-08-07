package co.yap.modules.setcardpin.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.setcardpin.interfaces.ISetCardPin
import co.yap.modules.setcardpin.viewmodels.SetCardPinViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import androidx.navigation.fragment.findNavController

open class SetCardPinFragment : BaseBindingFragment<ISetCardPin.ViewModel>(), ISetCardPin.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_set_card_pin

    override val viewModel: ISetCardPin.ViewModel
        get() = ViewModelProviders.of(this).get(SetCardPinViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setObservers()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                    val action = SetCardPinFragmentDirections.actionSetCardPinFragmentToConfirmCardPinFragment(viewModel.state.pincode)
                    findNavController().navigate(action)
                }
            }
        })
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}