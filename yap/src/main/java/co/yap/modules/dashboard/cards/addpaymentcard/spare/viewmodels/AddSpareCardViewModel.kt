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
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.extentions.toFormattedAmountWithCurrency
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.managers.MyUserManager
import kotlinx.coroutines.delay


class AddSpareCardViewModel(application: Application) :
    AddPaymentChildViewModel<IAddSpareCard.State>(application), IAddSpareCard.ViewModel,
    IRepositoryHolder<CardsRepository> {

    override val CONFIRM_VIRTUAL_PURCHASE: Int = 3000
    override val CONFIRM_PHYSICAL_PURCHASE: Int = 2000
    override var isFromBlockCardScreen: Boolean = false
    override var latitude: String = ""
    override var longitude: String = ""
    override var address: Address? = null
    override var paymentCard: Card? = null
    override var availableBalance: String = ""
    override var sharedPreferenceManager = SharedPreferenceManager(context)
    override var isFromaddressScreen: Boolean = false
    override val ADD_PHYSICAL_SPARE_SUCCESS_EVENT: Int = 1000
    override val REORDER_CARD_SUCCESS_EVENT: Int = 4000
    override val ADD_VIRTUAL_SPARE_SUCCESS_EVENT: Int = 5000
    override val repository: CardsRepository = CardsRepository
    private val repositoryTransaction: TransactionsRepository = TransactionsRepository
    override val addSparePhysicalCardLogicHelper: AddSparePhysicalCardLogicHelper =
        AddSparePhysicalCardLogicHelper(context, this)
    override val addSpareVirtualCardLogicHelper: AddSpareVirtualCardLogicHelper =
        AddSpareVirtualCardLogicHelper(context, this)
    override var cardType: String = ""
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: AddSpareCardState =
        AddSpareCardState()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        state.virtualCardFee = parentViewModel?.virtualCardFee?.toFormattedAmountWithCurrency()?:""
        state.physicalCardFee = parentViewModel?.physicalCardFee.toString()
        if (state.physicalCardFee == "" && state.virtualCardFee == "") {
            requestReorderCardFee("physical")
        }
    }

    override fun requestInitialData() {
        state.avaialableCardBalance =
            "AED ${MyUserManager.cardBalance.value?.availableBalance.toString().toFormattedCurrency()}"
        if (isFromBlockCardScreen || cardType != getString(R.string.screen_spare_card_landing_display_text_virtual_card)) {
            state.loading = true
            requestGetAddressForPhysicalCard()
        }
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_spare_card_landing_display_text_title))
        state.onChangeLocationClick = false
        toggleToolBarVisibility(true)

    }

    override fun onPause() {
        super.onPause()
        if (state.onChangeLocationClick) {
            toggleToolBarVisibility(false)
        }
        if (isFromBlockCardScreen) {
            toggleToolBarVisibility(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (state.onChangeLocationClick) {
            toggleToolBarVisibility(false)
        }
    }

    override fun requestAddSpareVirtualCard() {
        launch {
            state.loading = true
            when (val response = repository.addSpareVirtualCard(
                AddVirtualSpareCardRequest(MyUserManager.user?.currentCustomer?.getFullName())
            )) {
                is RetroApiResponse.Success -> {
                    paymentCard = response.data.data
                    clickEvent.setValue(ADD_VIRTUAL_SPARE_SUCCESS_EVENT)
                }

                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false

        }
    }

    override fun requestAddSparePhysicalCard() {
        val addPhysicalSpareCardRequest =
            AddPhysicalSpareCardRequest(
                MyUserManager.user?.currentCustomer?.getFullName(),
                address?.latitude.toString(),
                address?.longitude.toString(),
                address?.address1
            )

        launch {
            state.loading = true
            when (val response = repository.addSparePhysicalCard(
                addPhysicalSpareCardRequest
            )) {
                is RetroApiResponse.Success -> {
                    toggleToolBarVisibility(false)
                    clickEvent.setValue(ADD_PHYSICAL_SPARE_SUCCESS_EVENT)
                }
                is RetroApiResponse.Error -> {
                    state.toggleVisibility = false
                    state.toast = response.error.message
                }
            }
            state.loading = false

        }
    }

    override fun requestGetAddressForPhysicalCard() {
        launch {
            when (val response = repository.getUserAddressRequest()) {
                is RetroApiResponse.Success -> {
                    address = response.data.data
                    state.physicalCardAddressTitle = address?.address1 ?: ""

                    state.enableConfirmLocation = !address?.address1.isNullOrEmpty()

                    if (!address?.address2.isNullOrEmpty()) {
                        state.physicalCardAddressSubTitle = address?.address2?:""
                    } else {
                        state.physicalCardAddressSubTitle = " "
                    }
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }

            state.loading = false
        }
    }

    override fun updateAddressForPhysicalCard() {

    }

    override fun requestReorderCard() {
        launch {
            state.loading = true
            delay(1000)
            clickEvent.setValue(REORDER_CARD_SUCCESS_EVENT)
            state.loading = false
        }
    }

    override fun requestReorderCardFee(cardType: String) {
        launch {
            when (val response = repositoryTransaction.getCardFee(cardType)) {
                is RetroApiResponse.Success -> {
                    if (response.data.data != null) {
                        if (response.data.data?.feeType == Constants.FEE_TYPE_FLAT) {
                            val feeAmount = response.data.data?.tierRateDTOList?.get(0)?.feeAmount
                            val VATAmount = response.data.data?.tierRateDTOList?.get(0)?.vatAmount
                            state.physicalCardFee =
                                feeAmount?.plus(VATAmount ?: 0.0).toString()
                                    .toFormattedAmountWithCurrency()
                        }
                    } else {
                        state.physicalCardFee = "0.0".toFormattedAmountWithCurrency()
                    }
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
        }
    }
}