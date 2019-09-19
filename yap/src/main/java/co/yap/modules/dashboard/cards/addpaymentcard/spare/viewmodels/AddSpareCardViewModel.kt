package co.yap.modules.dashboard.cards.addpaymentcard.spare.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.spare.helpers.physical.AddSparePhysicalCardLogicHelper
import co.yap.modules.dashboard.cards.addpaymentcard.spare.helpers.virtual.AddSpareVirtualCardLogicHelper
import co.yap.modules.dashboard.cards.addpaymentcard.spare.interfaces.IAddSpareCard
import co.yap.modules.dashboard.cards.addpaymentcard.spare.states.AddSpareCardState
import co.yap.modules.dashboard.cards.addpaymentcard.viewmodels.AddPaymentChildViewModel
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.AddPhysicalSpareCardRequest
import co.yap.networking.cards.requestdtos.AddVirtualSpareCardRequest
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.SharedPreferenceManager


class AddSpareCardViewModel(application: Application) :
    AddPaymentChildViewModel<IAddSpareCard.State>(application), IAddSpareCard.ViewModel,
    IRepositoryHolder<CardsRepository> {

    lateinit var address: Address
    override var availableBalance: String = ""
    override var sharedPreferenceManager = SharedPreferenceManager(context)


    override var isFromaddressScreen: Boolean = false

    override val ADD_PHYSICAL_SPARE_CLICK_EVENT: Int = 0


    override val ADD_VIRTUAL_SPARE_CLICK_EVENT: Int = 0

    override val repository: CardsRepository = CardsRepository

    override val addSparePhysicalCardLogicHelper: AddSparePhysicalCardLogicHelper =
        AddSparePhysicalCardLogicHelper(context, this)

    override val addSpareVirtualCardLogicHelper: AddSpareVirtualCardLogicHelper =
        AddSpareVirtualCardLogicHelper(context, this)

    override var cardType: String = ""

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: AddSpareCardState =
        AddSpareCardState()

    override fun handlePressOnAddVirtualCardSuccess(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnConfirmPhysicalCardLocation(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnAddPhysicalCardSuccess(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnConfirmVirtualCardPurchase(id: Int) {
        requestAddSpareVirtualCard()
    }

    override fun handlePressOnConfirmPhysicalCardPurchase(id: Int) {
        requestAddSparePhysicalCard(id)
    }

    override fun handlePressOnConfirmLocation(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnChangeLocation(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
//
        if (!sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_AVAILABLE_BALANCE).isNullOrEmpty() && !sharedPreferenceManager.getValueString(
                SharedPreferenceManager.KEY_AVAILABLE_BALANCE
            ).equals("AVAILABLE_BALANCE")
        ) {
            availableBalance =
                sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_AVAILABLE_BALANCE) as String
        } else {
//            if (!cardType.isNullOrEmpty() && !cardType.equals(getString(R.string.screen_spare_card_landing_display_text_virtual_card))){
            requestGetAccountBalanceRequest()
//            }

        }

    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_spare_card_landing_display_text_title))
        toggleToolBarVisibility(true)
        state.onChangeLocationClick = false
        toggleToolBarVisibility(true)

    }

    override fun onPause() {
        super.onPause()
        if (state.onChangeLocationClick) {
            toggleToolBarVisibility(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (state.onChangeLocationClick) {
            toggleToolBarVisibility(false)
        }

    }

    //api
    override fun requestGetAccountBalanceRequest() {
        launch {
            state.loading = true
            when (val response = repository.getAccountBalanceRequest()) {
                is RetroApiResponse.Success -> {

                    sharedPreferenceManager.save(
                        SharedPreferenceManager.KEY_AVAILABLE_BALANCE,
                        response.data.data.currencyCode.toString() + " " + response.data.data.availableBalance.toString()
                    )

                    state.avaialableCardBalance =
                        response.data.data.currencyCode.toString() + " " + response.data.data.availableBalance.toString()
                    if (!cardType.isNullOrEmpty() && !cardType.equals(getString(R.string.screen_spare_card_landing_display_text_virtual_card))) {
                        requestGetAddressForPhysicalCard()
                    } else {
                        state.loading = false
                    }
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
        }

    }

    override fun requestAddSpareVirtualCard() {
        launch {
            state.loading = true
            when (val response = repository.addSpareVirtualCard(
                AddVirtualSpareCardRequest(" ")
            )) {
                is RetroApiResponse.Success -> {
                    clickEvent.setValue(ADD_VIRTUAL_SPARE_CLICK_EVENT)
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false

        }
    }

    override fun requestGetCardFeeRequest() {

    }

    override fun requestAddSparePhysicalCard(id: Int) {
        val addPhysicalSpareCardRequest: AddPhysicalSpareCardRequest =
            AddPhysicalSpareCardRequest(
                " ",
                address.latitude.toString(),
                address.longitude.toString(),
                address.address1
            )

        launch {
            state.loading = true
            when (val response = repository.addSparePhysicalCard(
                addPhysicalSpareCardRequest
            )) {
                is RetroApiResponse.Success -> {
                    clickEvent.setValue(ADD_PHYSICAL_SPARE_CLICK_EVENT)
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false

        }
    }

    override fun requestGetAddressForPhysicalCard() {
        launch {
            when (val response = repository.getUserAddressRequest()) {
                is RetroApiResponse.Success -> {
                    if (null != response.data.data) {
                        address = response.data.data
                        state.physicalCardAddressSubTitle = address.address1!!
                        if (!address.address2.isNullOrEmpty()) {
                            state.physicalCardAddressTitle = address.address2!!
                        } else {
                            state.physicalCardAddressTitle = " "
                        }
                    }

                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }

            state.loading = false
        }
    }

    override fun updateAddressForPhysicalCard() {

    }

}