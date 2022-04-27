package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.banklist

import android.app.Activity
import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.leansdk.LeanSdkManager
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseViewModel
import co.yap.networking.leanteach.LeanTechRepository
import co.yap.networking.leanteach.responsedtos.LeanOnBoardModel
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.networking.models.RetroApiResponse
import co.yap.widgets.search.IYapSearchView
import co.yap.yapcore.OnFilter
import co.yap.yapcore.helpers.extentions.toast
import com.uxcam.UXCam
import me.leantech.link.android.Lean

class BankListViewModel(application: Application) :
    AddMoneyBaseViewModel<IBankList.State>(application),
    IBankList.ViewModel {
    override val bankList: MutableLiveData<MutableList<BankListMainModel>> = MutableLiveData()
    override val bankListAdapter: BankListAdapter = BankListAdapter(mutableListOf())
    override var leanOnBoardModel: LeanOnBoardModel = LeanOnBoardModel()
    override var isPaymentJourneySet: MutableLiveData<Boolean> = MutableLiveData(false)
    private val leanTechRepository: LeanTechRepository = LeanTechRepository

    override val state: IBankList.State = BankListState()

    override fun getBankList() {
        launch {
            state.loading = true
            when (val response = leanTechRepository.bankList()) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    bankList.postValue(response.data.data)
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    toast(context, response.error.message)
                }
            }
        }
    }

    override fun startPaymentSourceJourney(bankIdentifier: String, activity: Activity?) {
        activity?.let { act ->
            UXCam.occludeSensitiveScreen(false)
            with(leanOnBoardModel) {
                LeanSdkManager.lean?.createPaymentSource(
                    act,
                    customerId.toString(),
                    bankIdentifier,
                    destinationId.toString(),
                    object : Lean.LeanListener {
                        override fun onResponse(status: Lean.LeanStatus) {
                            if (status.status == co.yap.modules.others.helper.Constants.SUCCESS_STATUS) {
                                isPaymentJourneySet.postValue(true)
                                UXCam.occludeSensitiveScreen(false)
                            } else toast(context, status.status)
                        }
                    }
                )
            }
        }
    }

    private val onFilter = object : OnFilter<BankListMainModel> {
        override fun onFilterApply(filter: CharSequence?, model: BankListMainModel): Boolean {
            return filter?.let {
                return model.name?.lowercase()?.contains(
                    it.toString().lowercase()
                ) == true
            } ?: false
        }
    }

    val yapSearchViewChangeListener = object : IYapSearchView {
        override fun onSearchActive(isActive: Boolean) {
            state.isSearchActive.value = isActive
        }

        override fun onTypingSearch(search: String?) {
            if (!search.isNullOrEmpty()) bankListAdapter.onSearch(
                search,
                onFilter
            ) else bankListAdapter.clearFilter()
        }

    }
}