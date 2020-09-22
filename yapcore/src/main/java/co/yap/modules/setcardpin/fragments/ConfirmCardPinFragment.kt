package co.yap.modules.setcardpin.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.modules.setcardpin.activities.SetCardPinWelcomeActivity
import co.yap.modules.setcardpin.pinflow.IPin
import co.yap.modules.setcardpin.pinflow.PINViewModel
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.databinding.FragmentPinBinding
import co.yap.yapcore.enums.AccountStatus
import co.yap.yapcore.enums.CardType
import co.yap.yapcore.helpers.DateUtils.LEAN_PLUM_EVENT_FORMAT
import co.yap.yapcore.helpers.livedata.GetAccountInfoLiveData
import co.yap.yapcore.leanplum.KYCEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.leanplum.trackEventInFragments
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_set_card_pin.*
import java.text.SimpleDateFormat
import java.util.*

open class ConfirmCardPinFragment : BaseBindingFragment<IPin.ViewModel>(), IPin.View {

    private val args: ConfirmCardPinFragmentArgs by navArgs()
    override val viewModel: IPin.ViewModel
        get() = ViewModelProviders.of(this).get(PINViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_pin

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setObservers()
        loadData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBindings().dialer.hideFingerprintView()
        getBindings().dialer.upDatedDialerPad(viewModel.state.pincode)
        getBindings().dialer.updateDialerLength(4)
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                    if (viewModel.pincode == viewModel.state.pincode) {
                        if (activity is SetCardPinWelcomeActivity) {
                            (activity as SetCardPinWelcomeActivity).card?.cardSerialNumber?.let { serialNumber ->
                                viewModel.setCardPin(
                                    serialNumber
                                )
                            }
                        }
                    } else {
                        dialer.startAnimation()
                    }
                }
                viewModel.EVENT_SET_CARD_PIN_SUCCESS -> {
                    if ((activity as? SetCardPinWelcomeActivity)?.card?.cardType == CardType.DEBIT.type) {
                        setDebitCardData()
                    } else {
                        findNavController().navigate(R.id.action_confirmCardPinFragment_to_setCardPinSuccessFragment)
                    }
                }
            }
        })
    }

    override fun loadData() {
        tvTitle.text = Translator.getString(
            requireContext(),
            Strings.screen_confirm_card_pin_display_text_title
        )
        btnAction.text = Translator.getString(
            requireContext(),
            Strings.screen_confirm_card_pin_button_create_pin
        )
        viewModel.pincode =
            arguments?.let { ConfirmCardPinFragmentArgs.fromBundle(it).pincode } as String

    }

    private fun setDebitCardData() {
        GetAccountInfoLiveData.get().observe(this, Observer {
            if (it!= null) {
                if (MyUserManager.user?.notificationStatuses == AccountStatus.CARD_ACTIVATED.name) {
                    trackEvent(KYCEvents.CARD_ACTIVE.type)
                    trackEventInFragments(
                        MyUserManager.user,
                        account_active = SimpleDateFormat(LEAN_PLUM_EVENT_FORMAT).format(
                            Calendar.getInstance().time
                        )
                    )
                    findNavController().navigate(R.id.action_confirmCardPinFragment_to_setCardPinSuccessFragment)
                } else findNavController().navigate(R.id.action_confirmCardPinFragment_to_setCardPinSuccessFragment)
            } else {
                viewModel.state.error = "Card activations failed"
            }
        })
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }

    private fun getBindings(): FragmentPinBinding {
        return viewDataBinding as FragmentPinBinding
    }
}