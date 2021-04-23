package co.yap.billpayments.dashboard

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.dashboard.mybills.adapter.BillModel
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.networking.customers.responsedtos.billpayment.BillResponse
import co.yap.networking.customers.responsedtos.billpayment.BillerCatalogModel
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.extentions.getJsonDataFromAsset
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay

class BillPaymentsViewModel(application: Application) :
    BaseViewModel<IBillPayments.State>(application),
    IBillPayments.ViewModel {
    override val state: IBillPayments.State = BillPaymentsState()
    override var billerCatalogs: MutableList<BillerCatalogModel> = mutableListOf()
    override var billcategories: MutableList<BillProviderModel> = mutableListOf()
    override var selectedBillerCatalog: BillerCatalogModel? = null
    override var onToolbarClickEvent: SingleClickEvent = SingleClickEvent()
    override var selectedBill: ViewBillModel? = null
    override var bills: MutableLiveData<MutableList<ViewBillModel>>? = MutableLiveData()
    override var billsAdapterList: MutableLiveData<MutableList<BillModel>>? = MutableLiveData()
    override fun onToolbarClick(id: Int) {
        onToolbarClickEvent.setValue(id)
    }

    override fun getViewBillsFromJSONFile(): BillResponse {
        val gson = GsonBuilder().create()
        return gson.fromJson<BillResponse>(
            context.getJsonDataFromAsset(
                "jsons/bill_list.json"
            ), object : TypeToken<BillResponse>() {}.type
        )
    }

    override fun getViewBills() {
        launch() {
            state.loading = true
            delay(1000L)
            val myBillResponse = getViewBillsFromJSONFile()
            val adapterList = mutableListOf<BillModel>()
            myBillResponse.viewBillList.forEach {
                it.billerInfo?.creationDate = DateUtils.reformatStringDate(
                    it.billerInfo?.creationDate.toString(),
                    DateUtils.SERVER_DATE_FULL_FORMAT,
                    DateUtils.FORMATE_DATE_MONTH_YEAR
                )
                it.billDueDate = DateUtils.reformatStringDate(
                    it.billDueDate.toString(),
                    DateUtils.SERVER_DATE_FULL_FORMAT,
                    DateUtils.FORMATE_DATE_MONTH_YEAR
                )
                adapterList.add(
                    BillModel(
                        creationDate = it.billerInfo?.creationDate,
                        nickName = it.billNickName,
                        currency = it.billerInfo?.currency,
                        billStatus = it.status,
                        billerName = it.billerInfo?.billerName,
                        amount = it.totalAmountDue,
                        logo = it.billerInfo?.logo,
                        dueDate = it.billDueDate
                    )
                )
            }
            bills?.value = myBillResponse.viewBillList.toMutableList()
            billsAdapterList?.value = adapterList
            state.loading = false
        }
    }

}
