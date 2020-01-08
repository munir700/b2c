package co.yap.household.onboarding.onboarding.viewmodels

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import co.yap.household.onboarding.onboarding.fragments.CardColorSelectionModel
import co.yap.household.onboarding.onboarding.fragments.HouseHoldCardSelectionAdapter
import co.yap.household.onboarding.onboarding.interfaces.IHouseHoldCardsSelection
import co.yap.household.onboarding.onboarding.states.HouseHoldCardsSelectionState
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.interfaces.OnItemClickListener
import kotlinx.coroutines.delay

class HouseHoldCardsSelectionViewModel(application: Application) :
    BaseViewModel<IHouseHoldCardsSelection.State>(application), IHouseHoldCardsSelection.ViewModel {
    override val state: HouseHoldCardsSelectionState = HouseHoldCardsSelectionState()
    override var adapter: HouseHoldCardSelectionAdapter =
        HouseHoldCardSelectionAdapter(context, mutableListOf())
    var adapterHouseHold = ObservableField<HouseHoldCardSelectionAdapter>()

    override fun onCreate() {
        super.onCreate()
        initViews()
        setUpItemClickListener()
    }

    override fun initViews() {
        getCardsColorListRequest()
    }

    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getCardsColorList(): MutableList<CardColorSelectionModel> {
        val list: MutableList<CardColorSelectionModel> = mutableListOf()
        list.add(CardColorSelectionModel(1, 2))
        list.add(CardColorSelectionModel(3, 4))
        return list
    }

    override fun getCardsColorListRequest() {
        launch {
            state.loading = true
            delay(2000)
            state.loading = false
            adapter.setList(getCardsColorList())
            /* when (val response =
                 mTransactionsRepository.getTransactionInternationalReasonList(productCode)) {
                 is RetroApiResponse.Success -> {
                     if (response.data.data.isNullOrEmpty()) return@launch
                     response.data.data?.let {
                         transactionData.addAll(it.map { item ->
                             InternationalFundsTransferReasonList.ReasonList(
                                 code = item.code ?: "",
                                 reason = item.reason ?: ""
                             )
                         })
                     }
                     //getTransactionInternationalfxList(productCode)
                     populateSpinnerData.value = transactionData
                 }
                 is RetroApiResponse.Error -> {
 //                    state.loading = false
                     state.toast = response.error.message
                 }
             }*/
        }
    }

    private fun setUpItemClickListener() {
        adapter.setItemListener(object : OnItemClickListener {
            override fun onItemClick(view: View, data: Any, pos: Int) {
                state.toast = "position is: $pos"
                // toast(requireContext(), "position is: " + pos.toString())
//                viewModel.clickEvent.setPayload(
//                    SingleClickEvent.AdaptorPayLoadHolder(
//                        view,
//                        data,
//                        pos
//                    )
//                )
//                viewModel.clickEvent.setValue(view.id)
            }
        })
    }

}