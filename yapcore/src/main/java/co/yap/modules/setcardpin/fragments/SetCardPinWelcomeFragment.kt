package co.yap.modules.setcardpin.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.modules.setcardpin.activities.SetCardPinWelcomeActivity
import co.yap.modules.setcardpin.interfaces.ISetCardPinWelcome
import co.yap.modules.setcardpin.viewmodels.SetCardPinWelcomeViewModel
import co.yap.yapcore.AdjustEvents.Companion.trackAdjustPlatformEvent
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.adjust.AdjustEvents

class SetCardPinWelcomeFragment : BaseBindingFragment<ISetCardPinWelcome.ViewModel>(),
    ISetCardPinWelcome.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_set_card_pin_welcome

    override val viewModel: ISetCardPinWelcome.ViewModel
        get() = ViewModelProviders.of(this).get(SetCardPinWelcomeViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is SetCardPinWelcomeActivity) {
            (activity as SetCardPinWelcomeActivity).preventTakeDeviceScreenShot.value = false
        }
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnCreatePin -> {
                    trackAdjustPlatformEvent(AdjustEvents.SET_PIN_START.type)
                    findNavController().navigate(R.id.action_setCardPinWelcomeFragment_to_setCardPinFragment)

                }
                R.id.tvCreatePinLater -> activity?.finish()
            }
        })
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}