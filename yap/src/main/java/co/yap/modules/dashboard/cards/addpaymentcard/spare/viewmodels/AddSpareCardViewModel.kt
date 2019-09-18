package co.yap.modules.dashboard.cards.addpaymentcard.spare.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.addpaymentcard.spare.helpers.physical.AddSparePhysicalCardLogicHelper
import co.yap.modules.dashboard.cards.addpaymentcard.spare.helpers.virtual.AddSpareVirtualCardLogicHelper
import co.yap.modules.dashboard.cards.addpaymentcard.spare.interfaces.IAddSpareCard
import co.yap.modules.dashboard.cards.addpaymentcard.spare.states.AddSpareCardState
import co.yap.modules.dashboard.cards.addpaymentcard.viewmodels.AddPaymentChildViewModel
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.AddPhysicalSpareCardRequest
import co.yap.networking.cards.requestdtos.AddVirtualSpareCardRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent


class AddSpareCardViewModel(application: Application) :
    AddPaymentChildViewModel<IAddSpareCard.State>(application), IAddSpareCard.ViewModel,
    IRepositoryHolder<CardsRepository> {

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
        clickEvent.setValue(id)

    }

    override fun handlePressOnConfirmPhysicalCardPurchase(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnConfirmLocation(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnChangeLocation(id: Int) {
        clickEvent.setValue(id)
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
    override fun getAccountBalanceRequest() {
        launch {
            when (val response = repository.getAccountBalanceRequest()) {
                is RetroApiResponse.Success -> {
                    state.virtualCardAvaialableBalance =
                        response.data.data.availableBalance.toString()

                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
        }
    }

    override fun addSpareVirtualCard() {
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


    override fun getCardFeeRequest() {

    }

    override fun addSparePhysicalCard() {
        val addPhysicalSpareCardRequest: AddPhysicalSpareCardRequest =
            AddPhysicalSpareCardRequest(" ", 0.00, 0.00, " ")

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

    override fun getAddressForPhysicalCard() {

    }

    override fun updateAddressForPhysicalCard() {

    }

}