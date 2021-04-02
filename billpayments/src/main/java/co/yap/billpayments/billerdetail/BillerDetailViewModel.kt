package co.yap.billpayments.billerdetail

import android.app.Application
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.translation.Strings
import co.yap.yapcore.enums.BillCategory

class BillerDetailViewModel(application: Application) :
    PayBillBaseViewModel<IBillerDetail.State>(application), IBillerDetail.ViewModel {
    override val state: IBillerDetail.State = BillerDetailState()

    override fun onCreate() {
        super.onCreate()

    }

    override fun onResume() {
        super.onResume()
        state.screenTitle.set(getScreenTitle(BillCategory.valueOf(parentViewModel?.selectedBillProvider?.categoryType.toString())))
        toggleToolBarVisibility(true)
    }

    override fun getScreenTitle(billCategory: BillCategory?): String {
        return if (billCategory == BillCategory.CREDIT_CARD) {
            getString(Strings.screen_biller_detail_title_text_credit_card)
        } else {
            getString(Strings.screen_biller_detail_title_text_enter_you_account_details)
        }
    }

//    override fun getBillerDetails(): MutableList<IoCatalogsModel> {
//        val gson = GsonBuilder().create()
//        return gson.fromJson<MutableList<IoCatalogsModel>>(
//            context.getJsonDataFromAsset(
//                "jsons/employment_describe_you_best.json"
//            ), object : TypeToken<List<IoCatalogsModel>>() {}.type
//        )
//    }

}
