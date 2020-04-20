package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.dashboard.more.main.viewmodels.MoreBaseViewModel
import co.yap.modules.dashboard.more.profile.intefaces.IPersonalDetail
import co.yap.modules.dashboard.more.profile.states.PersonalDetailState
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.OrderCardRequest
import co.yap.networking.cards.requestdtos.UpdateAddressRequest
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.EIDStatus
import co.yap.yapcore.managers.MyUserManager

class PersonalDetailsViewModel(application: Application) :
    MoreBaseViewModel<IPersonalDetail.State>(application), IPersonalDetail.ViewModel,
    IRepositoryHolder<CardsRepository> {

    override var UPDATE_ADDRESS_UI: Int = 10
    override var onUpdateAddressSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    override val orderCardSuccess: MutableLiveData<Boolean> = MutableLiveData()

    override val repository: CardsRepository = CardsRepository
    var address: Address? = null

    override fun onCreate() {
        super.onCreate()
        requestGetAddressForPhysicalCard()
    }
    override fun handlePressOnScanCard(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnEditPhone(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnEditEmail(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnEditAddress(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnDocumentCard(id: Int) {
        clickEvent.setValue(id)
    }

    override val state: PersonalDetailState = PersonalDetailState(application)

    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnBackButton() {

    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_personal_detail_display_text_title))
        state.fullName = MyUserManager.user?.currentCustomer?.getFullName() ?: ""
        state.phoneNumber =
            MyUserManager.user?.currentCustomer?.getFormattedPhoneNumber(context) ?: ""
        state.email = MyUserManager.user?.currentCustomer?.email ?: ""
        state.fullName = MyUserManager.user?.currentCustomer?.getFullName() ?: ""
        state.phoneNumber =
            MyUserManager.user?.currentCustomer?.getFormattedPhoneNumber(context) ?: ""
        state.email = MyUserManager.user?.currentCustomer?.email ?: ""
        setUpVerificationLayout()
    }

    private fun requestGetAddressForPhysicalCard() {
        state.loading = true
        launch {
            when (val response = repository.getUserAddressRequest()) {
                is RetroApiResponse.Success -> {

                    address = response.data.data
                    setUpAddressFields()
                    MyUserManager.userAddress = address
                    clickEvent.setValue(UPDATE_ADDRESS_UI)
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }

    private fun setUpAddressFields() {
        state.address = when {
            address?.address1 == address?.address2 -> address?.address1 ?: ""
            address?.address2.isNullOrBlank() && address?.address1.isNullOrBlank() -> ""
            address?.address1.isNullOrBlank() -> address?.address2 ?: ""
            address?.address2.isNullOrBlank() -> address?.address1 ?: ""
            else -> "${address?.address1}, ${address?.address2}"

        }
    }

    override fun toggleToolBar(hide: Boolean) {
        toggleToolBarVisibility(hide)
    }

    override fun updateToolBarText(heading: String) {
        setToolBarTitle(heading)
    }

    override fun requestUpdateAddress(updateAddressRequest: UpdateAddressRequest) {
        launch {
            state.loading = true
            when (val response = repository.editAddressRequest(updateAddressRequest)) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    onUpdateAddressSuccess.value = true

                }
                is RetroApiResponse.Error -> {
                    onUpdateAddressSuccess.value = false
                    state.error = response.error.message
                    state.loading = false
                }
            }
        }
    }

    override fun requestOrderCard(address: Address?) {
        address?.let {
            val orderCardRequest = OrderCardRequest(
                it.address1,
                "",
                it.address1,
                it.address2,
                it.latitude,
                it.longitude,
                "UAE", "Dubai"
            )
            launch {
                state.loading = true
                when (val response = repository.orderCard(orderCardRequest)) {
                    is RetroApiResponse.Success -> {
                        orderCardSuccess.value = true
                        state.loading = false
                    }

                    is RetroApiResponse.Error -> {
                        state.loading = false
                        orderCardSuccess.value = false
                        state.toast = response.error.message
//
                    }
                }
            }
        }
    }

    override fun setUpVerificationLayout() {
        when (MyUserManager.eidStatus) {
            EIDStatus.EXPIRED -> populateExpiredDocumentData()
            EIDStatus.VALID -> populateVerifiedDocumentData()
            EIDStatus.NOT_SET -> populateRequiredDocumentData()
        }
    }

    private fun populateVerifiedDocumentData() {
        state.drawbleRight =
            context.resources.getDrawable(co.yap.yapcore.R.drawable.ic_tick_enabled)
        state.verificationText = Translator.getString(
            context,
            Strings.screen_personal_details_display_text_emirates_id_details_update
        )
    }

    private fun populateExpiredDocumentData() {
        state.drawbleRight =
            context.resources.getDrawable(R.drawable.ic_doc_error)
        state.verificationText = Translator.getString(
            context,
            Strings.screen_personal_details_display_text_expired_emirates_id_details
        )
    }

    private fun populateRequiredDocumentData() {
        state.drawbleRight =
            context.resources.getDrawable(R.drawable.ic_doc_error)
        state.verificationText = Translator.getString(
            context,
            Strings.screen_personal_details_display_text_required_emirates_id_details
        )
    }

}