package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.banklist

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseViewModel
import co.yap.networking.leanteach.LeanTechRepository
import co.yap.networking.leanteach.responsedtos.LeanOnBoardModel
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.networking.models.RetroApiResponse
import co.yap.widgets.search.IYapSearchView
import co.yap.yapcore.OnFilter

class BankListViewModel(application: Application) :
    AddMoneyBaseViewModel<IBankList.State>(application),
    IBankList.ViewModel {
    override val bankList: MutableLiveData<MutableList<BankListMainModel>> = MutableLiveData()
    override val bankListAdapter: BankListAdapter = BankListAdapter(mutableListOf())
    override var leanOnBoardModel: LeanOnBoardModel = LeanOnBoardModel()
    private val leanTechRepository: LeanTechRepository = LeanTechRepository

    override val state: IBankList.State = BankListState()

    override fun getBankList() {
        launch {
            when (val response = leanTechRepository.bankList()) {
                is RetroApiResponse.Success -> {
                    //did this to only verify null/empty logo
                    var list = response.data.data
                    list?.get(0)?.logo =
                        "https://s3-eu-west-1.amazonaws.com//stg-yap-documents-public/profile_image/customer_data/1000001167/documents/PROFILE_PICTURE.jpeg"
                    list?.get(1)?.logo = ""
                    bankList.postValue(response.data.data)
                }
                is RetroApiResponse.Error -> {
                    response.error
                }
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
            if (!search.isNullOrEmpty()) bankListAdapter.onSearch(search, onFilter) else bankListAdapter.clearFilter()
        }

    }
}