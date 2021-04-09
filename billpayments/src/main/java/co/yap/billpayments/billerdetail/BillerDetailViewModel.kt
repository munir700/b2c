package co.yap.billpayments.billerdetail

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.billpayments.billerdetail.adapter.BillerDetailAdapter
import co.yap.billpayments.billerdetail.adapter.BillerDetailInputFieldModel
import co.yap.billpayments.billerdetail.composer.BillerDetailInputComposer
import co.yap.networking.customers.responsedtos.billpayment.BillerDetailResponse
import co.yap.networking.customers.responsedtos.billpayment.IoCatalogsModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.BillCategory
import co.yap.yapcore.helpers.extentions.getJsonDataFromAsset
import co.yap.yapcore.interfaces.OnItemClickListener
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay

class BillerDetailViewModel(application: Application) :
    PayBillBaseViewModel<IBillerDetail.State>(application), IBillerDetail.ViewModel {
    override val state: IBillerDetail.State = BillerDetailState()
    override var adapter: BillerDetailAdapter = BillerDetailAdapter(mutableListOf())
    override var billInputs: MutableLiveData<MutableList<BillerDetailInputFieldModel>> =
        MutableLiveData(mutableListOf())
    override val billerDetailItemComposer: BillerDetailInputComposer = BillerDetailInputComposer()
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        getBillerDetails()
        adapter.setItemListener(listener)
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

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            validate()
        }
    }

    private fun validate() {
        var isValid = false
        for (field in adapter.getDataList()) {
            if (field.minLength != null &&
                field.minLength!! > field.value?.get()?.length ?: 0
            )
            {
                isValid = false
                break
            } else {
                isValid = true
            }
        }
        state.valid.set(isValid)
    }

    override fun readBillerDetailsFromFile(): BillerDetailResponse {
        val gson = GsonBuilder().create()
        return gson.fromJson<BillerDetailResponse>(
            context.getJsonDataFromAsset(
                "jsons/biller_details.json"
            ), object : TypeToken<BillerDetailResponse>() {}.type
        )
    }

    override fun getBillerDetails() {
        launch {
            state.loading = true
            delay(200)
            val billerDetailResponse = readBillerDetailsFromFile()
            billInputs.value =
                billerDetailItemComposer.compose(billerDetailResponse.ioCatalogs as ArrayList<IoCatalogsModel>)
            adapter.setList(billInputs.value as ArrayList)
            state.loading = false

        }
    }
}
