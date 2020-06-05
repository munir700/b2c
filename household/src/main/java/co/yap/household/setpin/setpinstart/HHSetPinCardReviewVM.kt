package co.yap.household.setpin.setpinstart

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.enums.CardType
import javax.inject.Inject

class HHSetPinCardReviewVM @Inject constructor(override val state: IHHSetPinCardReview.State) :
    DaggerBaseViewModel<IHHSetPinCardReview.State>(), IHHSetPinCardReview.ViewModel,
    IRepositoryHolder<CardsRepository> {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val repository: CardsRepository = CardsRepository


    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {}
    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let { state.card?.value = it.getParcelable(Card::class.java.name) }

    }

    override fun handleClick(id: Int) {
        clickEvent.call()
    }

    override fun getCard() {
//        launch {
//            state.loading = true
//            when (val response = repository.getDebitCards(CardType.PREPAID.name)) {
//                is RetroApiResponse.Success -> {
//                    response.data.data?.let {
//                        if (it.isNotEmpty()) {
//                            val cardsList = response.data.data
//                            cardsList.let { cards ->
//                                card.value = cards?.get(0)
//                            }
//                        }
//                    }
//                }
//                is RetroApiResponse.Error -> state.toast = response.error.message
//            }
//            state.loading = false
//        }
    }

}