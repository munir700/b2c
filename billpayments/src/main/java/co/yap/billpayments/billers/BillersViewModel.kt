package co.yap.billpayments.billers

import android.app.Application
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.billpayments.billers.adapter.BillerModel
import co.yap.billpayments.billers.adapter.BillersAdapter
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.BillCategory

class BillersViewModel(application: Application) :
    PayBillBaseViewModel<IBillers.State>(application), IBillers.ViewModel {

    override val state: IBillers.State = BillersState()
    override var adapter: BillersAdapter = BillersAdapter(mutableListOf())
    override var billers: MutableList<BillerModel> = mutableListOf()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        parentViewModel?.billers = getBillerList()
        adapter.setList(parentViewModel?.billers?.toList() as List<BillerModel>)
        state.screenTitle.set(getScreenTitle(parentViewModel?.selectedBillCategory))
        state.showSearchView.set(parentViewModel?.selectedBillCategory == BillCategory.CREDIT_CARD)
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getToolbarString(parentViewModel?.selectedBillCategory!!))
        toggleToolBarVisibility(true)
    }

    override fun getToolbarString(billCategory: BillCategory?): String {
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
        return if (billCategory == BillCategory.CREDIT_CARD) {
            getString(Strings.screen_bill_payment_sub_heading_which_bank_is_your_card_issued_by)
        } else if (billCategory == BillCategory.RTA) {
            Translator.getString(
                context,
                Strings.screen_bill_payment_sub_heading_choose_from_the_list_below,
                " a transport provider "
            )
        } else {
            Translator.getString(
                context,
                Strings.screen_bill_payment_sub_heading_choose_from_the_list_below,
                ""
            )
        }
    }


    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getBillerList(): MutableList<BillerModel> {
        return listOf(
            BillerModel(
                name = "Abu Dhabi Commercial Bank",
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg"
            ),
            BillerModel(
                name = "Abu Dhabi Islamic Bank"
            ),
            BillerModel(
                name = "Abu Dhabi Bank",
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg"
            ),
            BillerModel(
                name = "Barclays Bank"
            ),
            BillerModel(
                name = "Abu Dhabi Bank"
            ),
            BillerModel(
                name = "Barclays Bank"
            ),
            BillerModel(
                name = "Abu Dhabi Bank"
            ),
            BillerModel(
                name = "Barclays Bank"
            ),
            BillerModel(
                name = "Abu Dhabi Bank"
            ),
            BillerModel(
                name = "Barclays Bank"
            ),
            BillerModel(
                name = "Abu Dhabi Bank"
            ),
            BillerModel(
                name = "Barclays Bank"
            )
        ).toMutableList()
    }
}
