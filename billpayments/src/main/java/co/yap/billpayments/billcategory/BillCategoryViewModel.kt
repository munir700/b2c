package co.yap.billpayments.billcategory

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.SingleClickEvent
import kotlinx.coroutines.delay

class BillCategoryViewModel(application: Application) :
    PayBillBaseViewModel<IBillCategory.State>(application), IBillCategory.ViewModel {
    override val state: IBillCategory.State = BillCategoryState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var billcategories: ObservableField<MutableList<BillProviderModel>> = ObservableField()

    override fun onCreate() {
        super.onCreate()
        if (parentViewModel?.billcategories.isNullOrEmpty())
            getBillCategoriesApi()
        else {
            billcategories.set(parentViewModel?.billcategories)
            state.dataPopulated.set(true)
        }
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(Translator.getString(context, Strings.screen_add_bill_toolbar_title))
    }

    private fun getBillCategories(): MutableList<BillProviderModel> {
        return mutableListOf(
            BillProviderModel(
                categoryId = "1",
                categoryName = "Credit Card",
                categoryType = "CREDIT_CARD",
                icon = "icon_biller_type_utility_creditcard"
            ),
            BillProviderModel(
                categoryId = "2",
                categoryName = "Telecom",
                categoryType = "TELECOM",
                icon = "icon_biller_type_telecom"
            ),
            BillProviderModel(
                categoryId = "3",
                categoryName = "Utilities",
                categoryType = "UTILITIES",
                icon = "icon_biller_type_utility"
            ),
            BillProviderModel(
                categoryId = "4",
                categoryName = "RTA",
                categoryType = "RTA",
                icon = "icon_biller_type_rta"
            ),
            BillProviderModel(
                categoryId = "5",
                categoryName = "Dubai Police",
                categoryType = "DUBAI_POLICE",
                icon = "icon_biller_type_police"
            )
        )
    }

    override fun handlePressView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getBillCategoriesApi() {
        launch {

            state.loading = true
            delay(1000L)
            billcategories.set(getBillCategories())
            state.dataPopulated.set(true)
            state.loading = false
        }
    }
}
