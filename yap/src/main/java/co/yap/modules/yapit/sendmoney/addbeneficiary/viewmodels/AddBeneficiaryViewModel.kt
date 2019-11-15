package co.yap.modules.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.IAddBeneficiary
import co.yap.modules.yapit.sendmoney.addbeneficiary.models.AddBeneficiaryData
import co.yap.modules.yapit.sendmoney.addbeneficiary.states.AddBeneficiaryStates
import co.yap.modules.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.sendmoney.AddBeneficiaryRequestDTO
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

class AddBeneficiaryViewModel(application: Application) :
    SendMoneyBaseViewModel<IAddBeneficiary.State>(application), IAddBeneficiary.ViewModel,
    IRepositoryHolder<CustomersRepository> {


    override val repository: CustomersRepository = CustomersRepository

    override val state: AddBeneficiaryStates = AddBeneficiaryStates(getApplication())

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnAddNow(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnAddDomestic(id: Int) {
        clickEvent.setValue(id)
    }

    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()


    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_add_beneficiary_display_text_title))
        toggleAddButtonVisibility(false)
    }

    fun requestAddBeneficiary() {
        var beneficiary: Beneficiary = Beneficiary()

        launch {
            state.loading = true
            when (val response = repository.addBeneficiary(beneficiary)) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message

                }
            }
        }
    }

//    override fun generateRequestDTO(beneficiaryData: AddBeneficiaryData): AddBeneficiaryRequestDTO {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//

    override fun generateRequestDTO(beneficiaryData: AddBeneficiaryData): AddBeneficiaryRequestDTO {
        val request = AddBeneficiaryRequestDTO()
        request.title(beneficiaryData.beneficiaryAccount().getNickName())
        request.beneficiaryType("CASHPAYOUT")
        request.currency(beneficiaryData.getCountry().getCurrency().getCode())
        request.country(beneficiaryData.getCountry().getCode())
        request.firstName(beneficiaryData.getBeneficiaryAccount().getFirstName())
        request.lastName(beneficiaryData.getBeneficiaryAccount().getLastName())
        request.mobileNo(beneficiaryData.getBeneficiaryAccount().getMobileNumber())

        return request
    }

    ///


//    fun handlePressOnNextButton() {
//        // send request
//        val request = generateRequestDTO(getParentActivityViewModel().getBeneficiaryData())
//        getRepository().addBeneficiary(request, object : MyCallBack<AddBeneficiaryResponseDTO>() {
//            fun onSuccess(response: AddBeneficiaryResponseDTO) {
//                if (response.getData() != null) {
//                    // navigate to next screen
//                    getParentActivityViewModel().setEditableBeneficiary(response.getData())
//                    getParentActivityViewModel().navigate(AddBeneficiaryRoute.ADD_BENEFICIARY_SUCCESS)
//                } else {
//                    getParentActivityViewModel().getState().setError(response.getMessage())
//                }
//            }
//
//            fun onFailure(error: AddBeneficiaryResponseDTO) {
//                if (error.getErrors().size() > 0) {
//                    getParentActivityViewModel().getState()
//                        .setError(error.getErrors().get(0).getMessage())
//                } else {
//                    getParentActivityViewModel().getState().setError(error.getMessage())
//                }
//
//            }
//        })
//
//    }

    ///
}