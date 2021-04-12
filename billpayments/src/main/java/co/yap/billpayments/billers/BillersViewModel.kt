package co.yap.billpayments.billers

import android.app.Application
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.billpayments.billers.adapter.BillersAdapter
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.widgets.State
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.BillCategory

class BillersViewModel(application: Application) :
    PayBillBaseViewModel<IBillers.State>(application), IBillers.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository
    override val state: IBillers.State = BillersState()
    override var adapter: BillersAdapter = BillersAdapter(mutableListOf())
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
    override fun onCreate() {
        super.onCreate()
        state.screenTitle.set(getScreenTitle(BillCategory.valueOf(parentViewModel?.selectedBillProvider?.categoryType.toString())))
        state.showSearchView.set(parentViewModel?.selectedBillProvider?.categoryType == BillCategory.CREDIT_CARD.name)
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getToolbarTitle(BillCategory.valueOf(parentViewModel?.selectedBillProvider?.categoryType.toString())))
        toggleToolBarVisibility(true)
    }

    override fun getToolbarTitle(billCategory: BillCategory?): String {
        return if (billCategory == BillCategory.CREDIT_CARD) {
            getString(Strings.screen_bill_payment_text_title_add_a_credit_card)
        } else {
            Translator.getString(
                context,
                Strings.screen_bill_payment_text_title_add_a_provider,
                billCategory?.title.toString()
            )
        }
    }

    override fun getScreenTitle(billCategory: BillCategory?): String {
        return when (billCategory) {
            BillCategory.CREDIT_CARD -> {
                getString(Strings.screen_bill_payment_sub_heading_which_bank_is_your_card_issued_by)
            }
            BillCategory.RTA -> {
                Translator.getString(
                    context,
                    Strings.screen_bill_payment_sub_heading_choose_from_the_list_below,
                    " a transport provider "
                )
            }
            else -> {
                Translator.getString(
                    context,
                    Strings.screen_bill_payment_sub_heading_choose_from_the_list_below,
                    ""
                )
            }
        }
    }

    override fun getBillerCatalogs(categoryId: String) {
        launch(Dispatcher.Background) {
            state.stateLiveData?.postValue(State.loading(null))
            val response = repository.getBillerCatalogs(categoryId)
            launch {
                when (response) {
                    is RetroApiResponse.Success -> {
                        state.viewState.value = false
                        adapter.setList(response.data.billerCatalogs)
                        parentViewModel?.billerCatalogs = adapter.getDataList()
                        state.stateLiveData?.value = if (adapter.getDataList()
                                .isNullOrEmpty()
                        ) State.empty(null) else State.success(null)
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                        showToast(response.error.message)
                        State.empty(null)
                    }
                }
            }
        }
    }
}
