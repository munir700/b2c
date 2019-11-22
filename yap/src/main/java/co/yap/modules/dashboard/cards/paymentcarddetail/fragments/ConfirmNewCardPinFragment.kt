package co.yap.modules.dashboard.cards.paymentcarddetail.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.activities.ChangeCardPinActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.activities.PaymentCardDetailActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.forgotcardpin.activities.ForgotCardPinActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels.ConfirmNewCardPinViewModel
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.setcardpin.fragments.ConfirmCardPinFragment
import co.yap.modules.setcardpin.interfaces.ISetCardPin
import co.yap.networking.customers.responsedtos.Customer
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.activity_create_passcode.*

open class ConfirmNewCardPinFragment : ConfirmCardPinFragment() {
    private val args: ConfirmNewCardPinFragmentArgs by navArgs()

    override val viewModel: ISetCardPin.ViewModel
        get() = ViewModelProviders.of(this).get(ConfirmNewCardPinViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity is ForgotCardPinActivity) {
            if ((activity as ForgotCardPinActivity).viewModel.state.currentScreen ==
                Constants.FORGOT_CARD_PIN_ACTION
            ) {
                // call service here
                viewModel.forgotCardPinRequest(
                    viewModel.state.cardSerialNumber,
                    viewModel.state.pincode

                )
            } else {
                loadData()
            }
        } else
            loadData()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                    if (viewModel.state.flowType == Constants.FORGOT_CARD_PIN_FLOW) {
                        val action =
                            ConfirmNewCardPinFragmentDirections.actionConfirmNewCardPinFragment2ToGenericOtpFragment3(
                                MyUserManager.user?.currentCustomer?.getFullName()!!,
                                false,
                                MyUserManager.user?.currentCustomer?.getFormattedPhoneNumber(
                                    requireContext()
                                )!!, Constants.FORGOT_CARD_PIN_ACTION
                            )
                        findNavController().navigate(action)
                    } else {
                        findNavController().navigate(R.id.action_confirmNewCardPinFragment_to_changePinSuccessFragment)
                    }

                }

                Constants.FORGOT_CARD_PIN_NAVIGATION -> {
                    findNavController().navigate(R.id.action_confirmNewCardPinFragment2_to_changePinSuccessFragment2)
                }
            }
        })
        viewModel.errorEvent.observe(this, Observer {
            dialer.startAnimationDigits()
        })
    }

    override fun loadData() {
        viewModel.state.oldPin = args.oldPinCode
        viewModel.state.newPin = args.newPinCode
        if (activity is ChangeCardPinActivity) {
            viewModel.state.cardSerialNumber = (activity as ChangeCardPinActivity).cardSerialNumber
        } else if (activity is ForgotCardPinActivity) {
            viewModel.state.cardSerialNumber =
                (activity as ForgotCardPinActivity).getCardSerialNumber()
            viewModel.state.flowType = Constants.FORGOT_CARD_PIN_FLOW
        }

    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()

    }
}