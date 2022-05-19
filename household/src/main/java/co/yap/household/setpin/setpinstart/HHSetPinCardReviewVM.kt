package co.yap.household.setpin.setpinstart

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.enums.CardType
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import co.yap.yapcore.managers.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HHSetPinCardReviewVM @Inject constructor(override val state: HHSetPinCardReviewState) :
    HiltBaseViewModel<IHHSetPinCardReview.State>(), IHHSetPinCardReview.ViewModel,
    IRepositoryHolder<CardsRepository> {
    override val repository: CardsRepository = CardsRepository


    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {}
    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let {
            state.card?.value = it.getParcelable(Card::class.java.name)
        }
        getCard()
    }

    override fun handleOnClick(id: Int) {}

    override fun getCard() {
        launch {
            state.loading = true
            when (val response = repository.getDebitCards(CardType.PREPAID.name)) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        if (it.isNotEmpty()) {
                            val cardsList = response.data.data
                            cardsList.let { cards ->
                                state.card?.value = cards?.get(0)
                                SessionManager.updateCard(cards?.get(0))
                            }
                        }
                    }
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }

}