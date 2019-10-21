package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController
import co.yap.modules.dashboard.more.profile.fragments.ChangePhoneNumberFragmentDirections
import co.yap.modules.dashboard.more.profile.intefaces.IChangePhoneNumber
import co.yap.modules.dashboard.more.profile.states.ChangePhoneNumberState
import co.yap.modules.dashboard.more.viewmodels.MoreBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateOtpGenericRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants

class ChangePhoneNumberViewModel(application: Application) :
    MoreBaseViewModel<IChangePhoneNumber.State>(application), IChangePhoneNumber.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val changePhoneNumberSuccessEvent: SingleClickEvent= SingleClickEvent()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override val state: ChangePhoneNumberState = ChangePhoneNumberState(application)
    override val repository: CustomersRepository = CustomersRepository
    private val messagesRepository: MessagesRepository = MessagesRepository

    override fun getCcp(etMobileNumber: EditText) {
        //  etMobileNumber.requestFocus()
        state.etMobileNumber = etMobileNumber
        state.etMobileNumber!!.requestFocus()
        state.etMobileNumber!!.setOnEditorActionListener(onEditorActionListener())
    }

    private fun validateMobileNumber(view: View) {
        launch {
            state.loading = true
            when (val response =
                repository.validatePhoneNumber("00971", state.mobile.replace(" ", ""))) {
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.errorMessage = response.error.message
                }

                is RetroApiResponse.Success -> {
                    createOtp(view)
                }
            }
        }
    }


    override fun onEditorActionListener(): TextView.OnEditorActionListener {
        return TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                /*if (state.valid){
                            handlePressOnNext()
                        }*/
            }
            false
        }
    }

    private fun createOtp(view: View) {
        launch {
            when (val response =
                messagesRepository.createOtpGenericWithPhone(phone = state.countryCode+ state.mobile.replace(" ", ""),createOtpGenericRequest = CreateOtpGenericRequest(Constants.CHANGE_MOBILE_NO))) {
                is RetroApiResponse.Success -> {
                    val action =
                        ChangePhoneNumberFragmentDirections.actionChangePhoneNumberFragmentToGenericOtpFragment(
                            otpType = Constants.CHANGE_MOBILE_NO,
                            mobileNumber = state.countryCode + " "+state.mobile
                        )
                    view.findNavController().navigate(action)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
            state.loading = false
        }
    }

    override fun changePhoneNumber() {
        launch {
            state.loading = true
            when (val response =
                repository.changeMobileNumber(countryCode = state.countryCode, mobileNumber = state.mobile.replace(" ", ""))) {
                is RetroApiResponse.Success -> {
                    changePhoneNumberSuccessEvent.call()
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
            state.loading = false
        }
    }



    override fun onHandlePressOnNextButton(view: View) {
        validateMobileNumber(view)
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle("")
    }
}