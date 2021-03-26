package co.yap.billpayments.mybills

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.billpayments.mybills.adapter.BillModel
import co.yap.billpayments.mybills.adapter.MyBillsAdapter
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.enums.BillStatus
import kotlinx.coroutines.delay

class MyBillsViewModel(application: Application) :
    PayBillBaseViewModel<IMyBills.State>(application), IMyBills.ViewModel {
    override val state: IMyBills.State = MyBillsState()
    override var adapter: MyBillsAdapter = MyBillsAdapter(mutableListOf())
    override var myBills: MutableLiveData<MutableList<BillModel>> = MutableLiveData()

    override fun onCreate() {
        super.onCreate()
        getMyBillsAPI()
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(Translator.getString(context, Strings.screen_my_bills_toolbar_text_title))
        toolgleRightIconVisibility(true)
    }

    private fun getMyBillList(): MutableList<BillModel> {
        return mutableListOf(
            BillModel(
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                name = "Etsilat",
                description = "29 Sept 2020 - Burj Telecom Residences",
                currency = "AED",
                amount = "100",
                billStatus = BillStatus.OVERDUE.title
            ),
            BillModel(
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                name = "Etsilat",
                description = "29 Sept 2020 - Burj Telecom  29 Sept 2020 - Burj Telecom Residences 29 Sept 2020 - Burj Telecom Residences 29 Sept 2020 - Burj Telecom Residences",
                currency = "AED",
                amount = "100",
                billStatus = BillStatus.OVERDUE.title
            ),
            BillModel(
                logoUrl = "",
                name = "Salik",
                description = "29 Sept 2020 - Burj Telecom Residences",
                currency = "AED",
                amount = "100",
                billStatus = BillStatus.BILL_DUE.title
            ),
            BillModel(
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                name = "Etsilat",
                description = "29 Sept 2020 - Burj Telecom Residences",
                currency = "AED",
                amount = "100",
                billStatus = BillStatus.OVERDUE.title
            ),
            BillModel(
                logoUrl = "",
                name = "Etsilat",
                description = "29 Sept 2020 - Burj Telecom Residences",
                currency = "USD",
                amount = "100",
                billStatus = BillStatus.BILL_DUE.title
            ),
            BillModel(
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                name = "Etsilat",
                description = "29 Sept 2020 - Burj Telecom Residences",
                currency = "USD",
                amount = "100",
                billStatus = BillStatus.PAID.title
            ),
            BillModel(
                logoUrl = "",
                name = "Etsilat",
                description = "29 Sept 2020 - Burj Telecom Residences",
                currency = "USD",
                amount = "100",
                billStatus = BillStatus.OVERDUE.title
            )
        )
    }

    private fun getMyBillsAPI() {
        launch {
            state.loading = true
            delay(1000L)
            myBills.postValue(getMyBillList())
            state.loading = false
        }
    }

    override fun onStop() {
        super.onStop()
        toolgleRightIconVisibility(false)
    }
}
