package co.yap.sendmoney.y2y.home.viewmodel

import android.app.Application
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.sendmoney.y2y.home.interfaces.IYapToYap
import co.yap.sendmoney.y2y.home.states.YapToYapState
import co.yap.sendmoney.y2y.main.viewmodels.Y2YBaseViewModel
import co.yap.widgets.recent_transfers.CoreRecentTransferAdapter
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.extentions.parseRecentItems

class YapToYapViewModel(application: Application) : Y2YBaseViewModel<IYapToYap.State>(application),
    IYapToYap.ViewModel, IRepositoryHolder<CustomersRepository> {
    override val state: IYapToYap.State = YapToYapState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var recentsAdapter: CoreRecentTransferAdapter = CoreRecentTransferAdapter(
        context,
        mutableListOf()
    )
    override val repository: CustomersRepository
        get() = CustomersRepository

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        if (parentViewModel?.isSearching?.value != null)
            toggleToolBarVisibility(parentViewModel?.isSearching?.value == false)
        else {
            toggleToolBarVisibility(false)
        }
        setToolBarTitle("Send to a YAP contact")
    }

    override fun getRecentBeneficiaries() {
        launch {
            when (val response = repository.getRecentY2YBeneficiaries()) {
                is RetroApiResponse.Success -> {
                    state.isNoRecents.set(response.data.data.isNullOrEmpty())
                    response.data.data?.let {
                        it.parseRecentItems()
                        recentsAdapter.setList(it)
                    }
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }

        }
    }
}