package co.yap.modules.dashboard.store.young.card

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.networking.cards.CardsRepository.getHouseHoldCardsDesign
import co.yap.networking.customers.responsedtos.HouseHoldCardsDesign
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.enums.AccountType
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import javax.inject.Inject
import kotlin.random.Random

class YoungCardEditDetailsVM @Inject constructor(
    override val state: IYoungCardEditDetails.State, override var validator: Validator?
) : DaggerBaseViewModel<IYoungCardEditDetails.State>(), IYoungCardEditDetails.ViewModel,IValidator {
    override val adapter: ObservableField<YoungCardEditAdapter>? = ObservableField()
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        getCardsDesignListRequest(
            mUserLiveData.value?.accountType ?: AccountType.B2C_HOUSEHOLD.name
        ) {
            it?.let {

                state.cardDesigns?.postValue(it)
            }
        }
    }

    override fun handleOnClick(id: Int) {
    }

    override fun getCardsDesignListRequest(
        accountType: String,
        apiResponse: ((MutableList<HouseHoldCardsDesign>?) -> Unit?)?
    ) {

        val cards: MutableList<HouseHoldCardsDesign> = mutableListOf()
        for (x in 0 until 5) {
            cards.add(
                HouseHoldCardsDesign(
                    "2019-09-19",
                    "https://s3-eu-west-1.amazonaws.com/dev-a-yap-documents-public/1568890204540_Error_Message.png",
                    "3567b3e6-0836-4316-84ee-0f02fa1177ca",
                    "qq",
                    "qq",
                    "",
                    "ACTIVE",
                    String.format("#%06x", Random.nextInt(0xffffff + 1)),
                    "cd",
                    "aq",
                    true
                )
            )
        }
        apiResponse?.invoke(cards)
        adapter?.get()?.setData(cards)
//        launch {
//            state.loading = true
//            when (val response =
//                getHouseHoldCardsDesign(accountType = accountType)) {
//                is RetroApiResponse.Success -> {
//                    if (response.data.data.isNullOrEmpty()) return@launch
//                    response.data.data?.let {
//                        apiResponse?.invoke(it)
//                        adapter?.get()?.setData(it)
//                    }
//                }
//                is RetroApiResponse.Error -> {
//                    state.loading = false
//                    apiResponse?.invoke(null)
//                    state.toast = response.error.message
//                }
//            }
//            state.loading = false
//        }
    }
}
