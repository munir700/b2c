package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.more.main.viewmodels.MoreBaseViewModel
import co.yap.modules.dashboard.more.profile.intefaces.IPersonalDetail
import co.yap.modules.dashboard.more.profile.states.PersonalDetailState
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.UpdateAddressRequest
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.managers.MyUserManager

class PersonalDetailsViewModel(application: Application) :
    MoreBaseViewModel<IPersonalDetail.State>(application), IPersonalDetail.ViewModel,
    IRepositoryHolder<CardsRepository> {

    override var UPDATE_ADDRESS_UI: Int = 10
    override var onUpdateAddressSuccess: MutableLiveData<Boolean> = MutableLiveData(false)

    override val repository: CardsRepository = CardsRepository
    lateinit var address: Address

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
        state.fullName = MyUserManager.user!!.currentCustomer.getFullName()
        state.phoneNumber = MyUserManager.user!!.currentCustomer.getFormattedPhoneNumber(context)
        state.email = MyUserManager.user?.currentCustomer?.email
        if (MyUserManager.userAddress == null) {
            requestGetAddressForPhysicalCard()
        } else {
            address = MyUserManager.userAddress!!
            setUpAddressFields()
        }
    }

    fun requestGetAddressForPhysicalCard() {
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
        var addresstitle = ""
        var addressDetail = ""

        if (!address.address2.isNullOrEmpty()) {
            addresstitle = address.address2!!
        }

        if (!address.address1.isNullOrEmpty()) {
            addressDetail = address.address1!!
        }

        state.address = addressDetail
    }

    override fun toggleToolBar(hide: Boolean) {
        toggleToolBarVisibility(hide)
    }

    override fun updateToolBarText(heading: String) {
        setToolBarTitle(heading)
    }

    override fun requestUpdateAddress(updateAddressRequest: UpdateAddressRequest) {
        state.error = ""
        launch {
            state.loading = true
            when (val response = repository.editAddressRequest(updateAddressRequest)) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    onUpdateAddressSuccess.value = true
                }

                is RetroApiResponse.Error -> {
                    state.error = response.error.message
                    state.loading = false
                }
            }
        }
    }
}