package co.yap.billpayments.billcategory

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.billpayments.billcategory.adapter.BillCategoryAdapter
import co.yap.billpayments.billcategory.adapter.BillCategoryModel
import co.yap.translation.Strings
import co.yap.translation.Translator
import kotlinx.coroutines.delay

class BillCategoryViewModel(application: Application) :
    PayBillBaseViewModel<IBillCategory.State>(application), IBillCategory.ViewModel {
    override val state: IBillCategory.State = BillCategoryState()
    override val adapter: BillCategoryAdapter = BillCategoryAdapter(mutableListOf())
    override var billcategories: MutableLiveData<MutableList<BillCategoryModel>> = MutableLiveData()

    override fun onResume() {
        super.onResume()
        setToolBarTitle(Translator.getString(context, Strings.screen_add_bill_toolbar_title))
        getBillCategoriesApi()
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

    private fun getBillCategoriesApi() {
        launch {
            state.loading = true
            delay(1000L)
            billcategories.postValue(getBillCategories())
            state.loading = false
        }
    }
}
