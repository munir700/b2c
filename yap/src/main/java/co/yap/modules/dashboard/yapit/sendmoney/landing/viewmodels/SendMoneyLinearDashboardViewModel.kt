package co.yap.modules.dashboard.yapit.sendmoney.landing.viewmodels

import android.app.Application
import co.yap.R
import co.yap.countryutils.country.utils.CurrencyUtils
import co.yap.modules.dashboard.yapit.sendmoney.landing.SendMoneyLinearDashboardAdapter
import co.yap.modules.dashboard.yapit.sendmoney.main.ISendMoneyLinearDashboard
import co.yap.modules.dashboard.yapit.sendmoney.main.SendMoneyCategoryType
import co.yap.modules.dashboard.yapit.sendmoney.main.SendMoneyLinearDashboardState
import co.yap.modules.dashboard.yapit.sendmoney.main.SendMoneyLinearOptions
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
import co.yap.yapcore.helpers.extentions.parseRecentItems
import co.yap.yapcore.managers.SessionManager

class SendMoneyLinearDashboardViewModel(application: Application) :
    BaseViewModel<ISendMoneyLinearDashboard.State>(application),
    ISendMoneyLinearDashboard.ViewModel, IRepositoryHolder<CustomersRepository> {
    override val state: SendMoneyLinearDashboardState = SendMoneyLinearDashboardState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val repository: CustomersRepository = CustomersRepository
    override var recentTransfers: ArrayList<Beneficiary> = arrayListOf()
    override var dashboardAdapter: SendMoneyLinearDashboardAdapter =
        SendMoneyLinearDashboardAdapter(
            mutableListOf(), null
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
        getAllRecentsBeneficiariesParallel()
        state.toolbarTitle = getString(Strings.common_send_money)
    }

    override fun getAllRecentsBeneficiariesParallel() {
        fetchRecentsApis { sendMoneyRecentsBeneficiariesResponse, y2yRecentBeneficiariesResponse ->
            launch(Dispatcher.Main) {
                recentTransfers.clear()
                when (sendMoneyRecentsBeneficiariesResponse) {
                    is RetroApiResponse.Success -> {
                        sendMoneyRecentsBeneficiariesResponse.data.data.parseRecentItems()
                        recentTransfers.addAll(sendMoneyRecentsBeneficiariesResponse.data.data)
                        state.viewState.value = false
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                    }
                }
                when (y2yRecentBeneficiariesResponse) {
                    is RetroApiResponse.Success -> {
                        y2yRecentBeneficiariesResponse.data.data?.parseRecentItems()
                        recentTransfers.addAll(
                            y2yRecentBeneficiariesResponse.data.data ?: emptyList()
                        )
                        state.viewState.value = false
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                    }
                }
                var jointList: List<Beneficiary> = ArrayList()
                jointList = recentTransfers.sortedByDescending {
                    it.lastUsedDate
                }
                state.isNoRecents.set(jointList.isNullOrEmpty())
                recentsAdapter.setList(jointList)
            }
        }
    }

    private fun fetchRecentsApis(
        responses: (RetroApiResponse<GetAllBeneficiaryResponse>?, RetroApiResponse<RecentBeneficiariesResponse>?) -> Unit
    ) {
        launch {
//            state.viewState.postValue(true)
//            coroutineScope {
            val deferredSMRecents = launchAsync { repository.getRecentBeneficiaries() }
            val deferredY2YRecents = launchAsync { repository.getRecentY2YBeneficiaries() }
            responses(deferredSMRecents.await(), deferredY2YRecents.await())
//            }
        }
    }

    override fun geSendMoneyOptions(): MutableList<SendMoneyLinearOptions> {
        val list = mutableListOf<SendMoneyLinearOptions>()
        list.add(
            SendMoneyLinearOptions(
                getString(Strings.screen_send_money_display_yap_to_yap_contact_title),
                R.drawable.ic_iconprofile,
                type = SendMoneyCategoryType.SendMoneyToYAPContacts,
                description = getString(Strings.screen_send_money_display_yap_to_yap_contact_description)
            )
        )
        list.add(
            SendMoneyLinearOptions(
                getString(Strings.screen_fragment_yap_it_add_money_text_qr_code),
                R.drawable.ic_qr_code,
                type = SendMoneyCategoryType.SendMoneyQRCode,
                description = getString(Strings.screen_send_money_display_qr_code_description)
            )
        )
        list.add(
            SendMoneyLinearOptions(
                getString(Strings.screen_send_money_local_bank_label),
                CurrencyUtils.getFlagDrawable(context, "AE"),
                type = SendMoneyCategoryType.SendMoneyToLocalBank,
                description = getString(Strings.screen_send_money_display_local_bank_description)
            )
        )
        if (SessionManager.homeCountry2Digit != "AE")
            list.add(
                SendMoneyLinearOptions(
                    getString(Strings.screen_send_money_home_label),
                    CurrencyUtils.getFlagDrawable(
                        context,
                        SessionManager.homeCountry2Digit
                    ), type = SendMoneyCategoryType.SendMoneyToHomeCountry,
                    description = getString(Strings.screen_send_money_display_home_country_description)
                )
            )
        list.add(
            SendMoneyLinearOptions(
                getString(Strings.screen_send_money_international_label),
                R.drawable.ic_international_transfer,
                type = SendMoneyCategoryType.SendMoneyToInternational,
                description = getString(Strings.screen_send_money_display_international_transfer_description)
            )
        )
        return list
    }
}