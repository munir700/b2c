package co.yap.modules.dashboard.yapit.sendmoney.landing.viewmodels

import android.app.Application
import co.yap.R
import co.yap.countryutils.country.utils.CurrencyUtils
import co.yap.modules.dashboard.yapit.sendmoney.landing.SendMoneyDashboardAdapter
import co.yap.modules.dashboard.yapit.sendmoney.main.ISendMoneyDashboard
import co.yap.modules.dashboard.yapit.sendmoney.main.SendMoneyDashboardState
import co.yap.modules.dashboard.yapit.sendmoney.main.SendMoneyOptions
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.beneficiary.RecentBeneficiariesResponse
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.customers.responsedtos.sendmoney.GetAllBeneficiaryResponse
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.widgets.recent_transfers.CoreRecentTransferAdapter
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.managers.SessionManager
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class SendMoneyDashboardViewModel(application: Application) :
    BaseViewModel<ISendMoneyDashboard.State>(application),
    ISendMoneyDashboard.ViewModel, IRepositoryHolder<CustomersRepository> {
    override val state: SendMoneyDashboardState = SendMoneyDashboardState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val repository: CustomersRepository = CustomersRepository
    override var recentTransfers: ArrayList<Beneficiary> = arrayListOf()
    override var dashboardAdapter: SendMoneyDashboardAdapter = SendMoneyDashboardAdapter(
        context,
        mutableListOf()
    )
    override var recentsAdapter: CoreRecentTransferAdapter = CoreRecentTransferAdapter(
        context,
        mutableListOf()
    )

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        state.toolbarTitle = getString(Strings.common_send_money)
        dashboardAdapter.setList(geSendMoneyOptions())
        getAllRecentsBeneficiariesParallel()
    }

    override fun getAllRecentsBeneficiariesParallel() {
        fetchRecentsApis { sendMoneyRecentsBeneficiariesResponse, y2yRecentBeneficiariesResponse ->
            launch(Dispatcher.Main) {
                when (sendMoneyRecentsBeneficiariesResponse) {
                    is RetroApiResponse.Success -> {
                        sendMoneyRecentsBeneficiariesResponse.data.data.forEach {
                            it.name = it.fullName()
                            it.profilePictureUrl = it.beneficiaryPictureUrl
                            it.type = it.beneficiaryType
                            it.isoCountryCode = it.country
                        }
                        recentTransfers.addAll(sendMoneyRecentsBeneficiariesResponse.data.data)
                        recentTransfers.sortedByDescending { it.lastUsedDate }
                        recentsAdapter.setList(recentTransfers)
                        state.viewState.value = false
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                    }
                }
                when (y2yRecentBeneficiariesResponse) {
                    is RetroApiResponse.Success -> {
                        y2yRecentBeneficiariesResponse.data.data?.forEach {
                            it.name = it.fullName()
                            it.profilePictureUrl = it.beneficiaryPictureUrl
                        }
                        recentTransfers.addAll(
                            y2yRecentBeneficiariesResponse.data.data ?: emptyList()
                        )
                        recentTransfers.sortedByDescending { it.lastUsedDate }
                        recentsAdapter.setList(recentTransfers)
                        state.viewState.value = false
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                    }
                }
            }
        }

    }

    private fun fetchRecentsApis(
        responses: (RetroApiResponse<GetAllBeneficiaryResponse>?, RetroApiResponse<RecentBeneficiariesResponse>?) -> Unit
    ) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            coroutineScope {
                val deferredSMRecents = async { repository.getRecentBeneficiaries() }
                val deferredY2YRecents = async { repository.getRecentY2YBeneficiaries() }
                responses(deferredSMRecents.await(), deferredY2YRecents.await())
            }
        }
    }


    override fun geSendMoneyOptions(): MutableList<SendMoneyOptions> {
        val list = mutableListOf<SendMoneyOptions>()
        list.add(
            SendMoneyOptions(
                getString(Strings.screen_y2y_display_button_yap_contacts),
                R.drawable.ic_iconprofile,
                false,
                null
            )
        )
        list.add(
            SendMoneyOptions(
                getString(Strings.screen_send_money_local_bank_label),
                R.drawable.ic_bankicon,
                true,
                CurrencyUtils.getFlagDrawable(context, "AE")
            )
        )
        list.add(
            SendMoneyOptions(
                getString(Strings.screen_send_money_international_label),
                R.drawable.ic_bankicon,
                true,
                null
            )
        )
        list.add(
            SendMoneyOptions(
                getString(Strings.screen_send_money_home_label),
                R.drawable.ic_houseicon,
                false,
                CurrencyUtils.getFlagDrawable(
                    context,
                    SessionManager.user?.currentCustomer?.homeCountry ?: ""
                )
            )
        )
        list.add(
            SendMoneyOptions(
                getString(Strings.screen_fragment_yap_it_add_money_text_qr_code),
                R.drawable.ic_qr_code,
                false,
                null
            )
        )
        return list
    }
}
