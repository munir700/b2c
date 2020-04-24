package co.yap.modules.setcardpin.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.modules.setcardpin.activities.SetCardPinWelcomeActivity
import co.yap.modules.setcardpin.interfaces.ISetCardPin
import co.yap.modules.setcardpin.viewmodels.SetCardPinViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.databinding.FragmentSetCardPinBinding

open class SetCardPinFragment : BaseBindingFragment<ISetCardPin.ViewModel>(), ISetCardPin.View {


    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_set_card_pin

    override val viewModel: ISetCardPin.ViewModel
        get() = ViewModelProviders.of(this).get(SetCardPinViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBindings().dialer.hideFingerprintView()
        getBindings().dialer.upDatedDialerPad(viewModel.state.pincode)
        getBindings().dialer.updateDialerLength(4)
        if (activity is SetCardPinWelcomeActivity) {
            (activity as SetCardPinWelcomeActivity).preventTakeDeviceScreenShot.value = true
        }
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                    val action =
                        SetCardPinFragmentDirections.actionSetCardPinFragmentToConfirmCardPinFragment(
                            viewModel.state.pincode
                        )
                    findNavController().navigate(action)
                }
            }
        })
        viewModel.errorEvent.observe(this, Observer {

        })
    }

    override fun loadData() {

    }

    fun getBindings(): FragmentSetCardPinBinding {
        return viewDataBinding as FragmentSetCardPinBinding
    }


    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}