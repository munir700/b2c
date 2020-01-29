package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IBeneficiaryAccountDetails
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.BeneficiaryAccountDetailsState
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateOtpGenericRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.SendMoneyBeneficiaryType

class BeneficiaryAccountDetailsViewModel(application: Application) :
    SendMoneyBaseViewModel<IBeneficiaryAccountDetails.State>(application),
    IBeneficiaryAccountDetails.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val success: MutableLiveData<Boolean> = MutableLiveData(false)
    override val isBeneficiaryValid: MutableLiveData<Boolean>  = MutableLiveData(false)
    override val state: BeneficiaryAccountDetailsState = BeneficiaryAccountDetailsState()
    override val repository: CustomersRepository = CustomersRepository
    private val messagesRepository: MessagesRepository = MessagesRepository
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var beneficiary: Beneficiary? = Beneficiary()
    override val otpCreateObserver: MutableLiveData<Boolean> = MutableLiveData()


    override fun onCreate() {
        super.onCreate()
        parentViewModel?.beneficiary?.value?.beneficiaryType?.let { beneficiaryType ->
            if (beneficiaryType.isNotEmpty())
                when (SendMoneyBeneficiaryType.valueOf(beneficiaryType)) {
                    SendMoneyBeneficiaryType.SWIFT -> {
                        state.showlyIban.set(true)
                        //state.showlyConfirmIban.set(true)
                    }
                    SendMoneyBeneficiaryType.RMT -> {
                        state.showlyIban.set(true)
                        //state.showlyConfirmIban.set(true)
                    }
                    else -> {

                    }
                }
        }
        parentViewModel?.beneficiary?.value?.let {
            state.bankName = it.bankName ?: ""
            if (it.identifierCode1.isNullOrEmpty()) {
                if (it.identifierCode2.isNullOrEmpty()) {
                    state.idCode = ""
                } else {
                    state.idCode = "ID Code: " + it.identifierCode2
                }
            } else {
                state.idCode = "ID Code: " + it.identifierCode1
            }

            state.bankAddress = it.branchName ?: "" + it.branchAddress ?: ""
            state.bankPhoneNumber = ""
        }
    }

    override fun handlePressOnAddBank(id: Int) {
        if (id == R.id.confirmButton) {
            parentViewModel?.beneficiary?.value?.beneficiaryType?.let { it ->
                if (it.isNotEmpty())
                    when (SendMoneyBeneficiaryType.valueOf(it)) {

                        SendMoneyBeneficiaryType.SWIFT,SendMoneyBeneficiaryType.RMT -> {
                            validateBeneficiaryDetails()
                            //createOtp(Constants.SWIFT_BENEFICIARY)
//                            parentViewModel?.beneficiary?.value?.accountNo = state.accountIban
//                            createBeneficiaryRequest()
                        }
//                        SendMoneyBeneficiaryType.RMT -> {
//                            validateBeneficiaryDetails()
//                            //createOtp(Constants.RMT_BENEFICIARY)
////                            parentViewModel?.beneficiary?.value?.accountNo = state.accountIban
////                            createBeneficiaryRequest()
//                        }
                        else -> {
                            clickEvent.setValue(id)
                        }
                    }
            }
        } else
            clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_add_beneficiary_display_text_title))
        parentViewModel?.state?.toolbarVisibility?.set(true)
        parentViewModel?.state?.leftIcon?.set(true)
        //toggleAddButtonVisibility(false)
    }

    override fun createBeneficiaryRequest() {
        parentViewModel?.beneficiary?.value?.let {
            launch {
                state.loading = true
                when (val response = repository.addBeneficiary(it)) {
                    is RetroApiResponse.Success -> {
                        state.loading = false
                        success.value = true
                        beneficiary = response.data.data
                    }

                    is RetroApiResponse.Error -> {
                        state.loading = false
                        state.toast = response.error.message
                        success.value = false
                    }
                }
            }
        }
    }

    override fun validateBeneficiaryDetails() {
        parentViewModel?.beneficiary?.value?.let {
            it.accountNo = state.accountIban.replace(" ", "")
            launch {
                state.loading = true
                when (val response = repository.validateBeneficiary(it)) {
                    is RetroApiResponse.Success -> {
                        state.loading = false
                        isBeneficiaryValid.value = true


                    }

                    is RetroApiResponse.Error -> {
                        state.loading = false
                        isBeneficiaryValid.value = false
                        state.toast = response.error.message
                        //success.value = false
                    }
                }
            }
        }

    }

    override fun createOtp(action: String) {
        launch {
            state.loading = true
            when (val response =
                messagesRepository.createOtpGeneric(
                    createOtpGenericRequest = CreateOtpGenericRequest(
                        action
                    )
                )) {
                is RetroApiResponse.Success -> {
                    otpCreateObserver.value = true
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
            state.loading = false
        }
    }

    override fun retry() {
    }
}