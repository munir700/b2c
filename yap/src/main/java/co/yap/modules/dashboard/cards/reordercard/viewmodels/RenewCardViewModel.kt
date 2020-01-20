package co.yap.modules.dashboard.cards.reordercard.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.cards.reordercard.interfaces.IRenewCard
import co.yap.modules.dashboard.cards.reordercard.states.RenewCardState
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.ReorderCardRequest
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.CardType
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.managers.MyUserManager

class RenewCardViewModel(application: Application) :
    ReorderCardBaseViewModel<IRenewCard.State>(application), IRenewCard.ViewModel,
    IRepositoryHolder<TransactionsRepository> {
    override var reorderCardSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val repository: TransactionsRepository = TransactionsRepository
    private val cardRepository: CardsRepository = CardsRepository
    override var fee: String = "0.0"
    override var address: Address = Address()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override val state: IRenewCard.State = RenewCardState()

    override fun onCreate() {
        super.onCreate()
        state.availableCardBalance.set("AED ${Utils.getFormattedCurrency(MyUserManager.cardBalance.value?.availableBalance.toString())}")
        requestReorderCardFee(parentViewModel?.card?.cardType)
        requestGetAddressForPhysicalCard()
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle("Reorder new card")
        parentViewModel?.state?.toolbarVisibility?.set(true)
        updateCardTypeState()
    }

    private fun updateCardTypeState() {
        if (parentViewModel?.card?.cardType == CardType.DEBIT.type)
            state.cardType.set("Primary Card")
        else
            state.cardType.set(CardType.PHYSICAL.type + " Card")
    }


    override fun requestReorderCardFee(cardType: String?) {
        cardType?.let {
            when (it) {
                CardType.DEBIT.type -> {
                    requestReorderDebitCardFee()
                }
                else -> {
                    requestReorderSupplementaryCardFee()
                }
            }
        }
    }

    override fun requestReorderCard() {
        val reorderCardRequest = ReorderCardRequest(
            parentViewModel?.card?.cardSerialNumber,
            address.address1,
            address.latitude.toString(),
            address.longitude.toString()
        )
        parentViewModel?.card?.cardType?.let {
            when (it) {
                CardType.DEBIT.type -> {
                    requestReorderDebitCard(reorderCardRequest)
                }
                else -> {
                    requestReorderSupplementaryCard(reorderCardRequest)
                }
            }
        }
    }

    override fun requestReorderDebitCardFee() {
        launch {
            when (val response = repository.getDebitCardFee()) {
                is RetroApiResponse.Success -> {
                    fee = response.data.data?.amount ?: "0.0"
                    state.cardFee.set("${response.data.data?.currency} ${response.data.data?.amount}")
                }

                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
        }
    }

    override fun requestReorderSupplementaryCardFee() {
        launch {
            when (val response = repository.getCardFee(CardType.PHYSICAL.type)) {
                is RetroApiResponse.Success -> {
                    fee = response.data.data?.amount ?: "0.0"
                    state.cardFee.set("${response.data.data?.currency} ${response.data.data?.amount}")
                }

                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
        }
    }

    override fun requestReorderDebitCard(reorderCardRequest: ReorderCardRequest) {
        launch {
            state.loading = true
            when (val response = cardRepository.reorderDebitCard(reorderCardRequest)) {
                is RetroApiResponse.Success -> {
                    reorderCardSuccess.value = true
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

    override fun requestReorderSupplementaryCard(reorderCardRequest: ReorderCardRequest) {
        launch {
            state.loading = true
            when (val response = cardRepository.reorderSupplementryCard(reorderCardRequest)) {
                is RetroApiResponse.Success -> {
                    reorderCardSuccess.value = true
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

    override fun requestGetAddressForPhysicalCard() {
        launch {
            state.loading = true
            when (val response = cardRepository.getUserAddressRequest()) {
                is RetroApiResponse.Success -> {
                    address = response.data.data
                    state.cardAddressTitle.set(address.address1 ?: "")
                    state.valid.set(!address.address1.isNullOrEmpty())
                    if (!address.address2.isNullOrEmpty()) {
                        state.cardAddressSubTitle.set(address.address2 ?: "")
                    }
                }
                is RetroApiResponse.Error -> {
                    state.valid.set(false)
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

}