package co.yap.modules.dashboard.cards.paymentcarddetail.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.setcardpin.pinflow.IPin
import co.yap.modules.setcardpin.pinflow.PINViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.databinding.FragmentPinBinding

class ChangeCardPinFragment : BaseBindingFragment<IPin.ViewModel>(), IPin.View {
    override val viewModel: IPin.ViewModel
        get() = ViewModelProviders.of(this).get(PINViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_pin

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setObservers()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setChangeCardPinFragmentData()
        viewModel.state.forgotTextVisibility=true
        getBindings().dialer.hideFingerprintView()
        getBindings().dialer.upDatedDialerPad(viewModel.state.pincode)
        getBindings().dialer.updateDialerLength(4)
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                   /* val action =
                        ChangeCardPinFragmentDirections.actionChangeCardPinFragmentToSetNewCardPinFragment(
                            "",
                            viewModel.state.pincode
                        )
                    findNavController().navigate(action)*/
                }
            }
        })
    }

    override fun loadData() {

    }

    private fun getBindings(): FragmentPinBinding {
        return viewDataBinding as FragmentPinBinding
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}