package co.yap.billpayments.billers

import android.app.Application
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.billpayments.billers.adapter.BillersAdapter
import co.yap.networking.customers.responsedtos.billpayment.BillerModel
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
        state.screenTitle.set(getScreenTitle(BillCategory.valueOf(parentViewModel?.selectedBillProvider?.categoryType.toString())))
        state.showSearchView.set(parentViewModel?.selectedBillProvider?.categoryType == BillCategory.CREDIT_CARD.name)
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getToolbarString(BillCategory.valueOf(parentViewModel?.selectedBillProvider?.categoryType.toString())))
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
                billerID = "1",
                billerName = "Etisalat",
                billerType = "Utility",
                countryName = "Dubai",
                countryCode = "UAE",
                billerDescription = "Electricity",
                logo = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg"
            ),
            BillerModel(
                billerID = "1",
                billerName = "Etisalat",
                billerType = "Utility",
                countryName = "Dubai",
                countryCode = "UAE",
                billerDescription = "Electricity",
                logo = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg"
            ),
            BillerModel(
                billerID = "1",
                billerName = "Etisalat",
                billerType = "Utility",
                countryName = "Dubai",
                countryCode = "UAE",
                billerDescription = "Electricity",
                logo = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg"
            ),
            BillerModel(
                billerID = "1",
                billerName = "Etisalat",
                billerType = "Utility",
                countryName = "Dubai",
                countryCode = "UAE",
                billerDescription = "Electricity",
                logo = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg"
            ),
            BillerModel(
                billerID = "1",
                billerName = "Etisalat",
                billerType = "Utility",
                countryName = "Dubai",
                countryCode = "UAE",
                billerDescription = "Electricity",
                logo = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg"
            ),
            BillerModel(
                billerID = "1",
                billerName = "Etisalat",
                billerType = "Utility",
                countryName = "Dubai",
                countryCode = "UAE",
                billerDescription = "Electricity",
                logo = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg"
            ),
            BillerModel(
                billerID = "1",
                billerName = "Etisalat",
                billerType = "Utility",
                countryName = "Dubai",
                countryCode = "UAE",
                billerDescription = "Electricity",
                logo = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg"
            ),
            BillerModel(
                billerID = "1",
                billerName = "Etisalat",
                billerType = "Utility",
                countryName = "Dubai",
                countryCode = "UAE",
                billerDescription = "Electricity",
                logo = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg"
            ),
            BillerModel(
                billerID = "1",
                billerName = "Etisalat",
                billerType = "Utility",
                countryName = "Dubai",
                countryCode = "UAE",
                billerDescription = "Electricity",
                logo = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg"
            ),
            BillerModel(
                billerID = "1",
                billerName = "Etisalat",
                billerType = "Utility",
                countryName = "Dubai",
                countryCode = "UAE",
                billerDescription = "Electricity",
                logo = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg"
            ),
            BillerModel(
                billerID = "1",
                billerName = "Etisalat",
                billerType = "Utility",
                countryName = "Dubai",
                countryCode = "UAE",
                billerDescription = "Electricity",
                logo = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg"
            ),
            BillerModel(
                billerID = "1",
                billerName = "Etisalat",
                billerType = "Utility",
                countryName = "Dubai",
                countryCode = "UAE",
                billerDescription = "Electricity",
                logo = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg"
            )
        ).toMutableList()
    }

    override fun getBillers() {
        launch {
            state.loading = true
            parentViewModel?.billers = getBillerList()
            adapter.setList(parentViewModel?.billers?.toList() as List<BillerModel>)
            state.loading = false
        }
    }
}
