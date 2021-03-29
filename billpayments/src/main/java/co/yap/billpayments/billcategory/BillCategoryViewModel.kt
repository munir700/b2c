package co.yap.billpayments.billcategory

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.SingleClickEvent
import kotlinx.coroutines.delay

class BillCategoryViewModel(application: Application) :
    PayBillBaseViewModel<IBillCategory.State>(application), IBillCategory.ViewModel {
    override val state: IBillCategory.State = BillCategoryState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override var billcategories: ObservableField<MutableList<BillCategoryModel>> = ObservableField()

    override fun onCreate() {
        super.onCreate()
        getBillCategoriesApi()
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(Translator.getString(context, Strings.screen_add_bill_toolbar_title))
    }

    private fun getBillCategories(): MutableList<BillCategoryModel> {
        return mutableListOf(
            BillCategoryModel(
                categoryId = "1",
                categoryName = "Credit Card",
                icon = "icon_biller_type_utility_creditcard"
            ),
            BillCategoryModel(
                categoryId = "1",
                categoryName = "Telecom",
                icon = "icon_biller_type_telecom"
            ),
            BillCategoryModel(
                categoryId = "1",
                categoryName = "Utilities",
                icon = "icon_biller_type_utility"
            ),
            BillCategoryModel(
                categoryId = "1",
                categoryName = "RTA",
                icon = "icon_biller_type_rta"
            ),
            BillCategoryModel(
                categoryId = "1",
                categoryName = "Dubai Police",
                icon = "icon_biller_type_police"
            )
        )
    }

    override fun handlePressView(id: Int) {
        clickEvent.setValue(id)
    }

    private fun getBillCategoriesApi() {
        launch {
            state.loading = true
            delay(1000L)
            billcategories.set(getBillCategories())
            state.loading = false
        }
    }
}
