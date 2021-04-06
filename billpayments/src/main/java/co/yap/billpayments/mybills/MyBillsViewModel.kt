package co.yap.billpayments.mybills

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.billpayments.mybills.adapter.MyBillsAdapter
import co.yap.networking.customers.responsedtos.billpayment.BillModel
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.BillStatus
import co.yap.yapcore.managers.SessionManager
import kotlinx.coroutines.delay

class MyBillsViewModel(application: Application) :
    PayBillBaseViewModel<IMyBills.State>(application), IMyBills.ViewModel {
    override val state: IMyBills.State = MyBillsState()
    override var adapter: MyBillsAdapter = MyBillsAdapter(mutableListOf())
    override var myBills: MutableLiveData<MutableList<BillModel>> = MutableLiveData()
    override var selectedBills: MutableList<BillModel> = mutableListOf()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
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
                description = "29 Sept 2020",
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
                description = "29 Sept 2020",
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

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getMyBillsAPI() {
        launch {
            state.loading = true
            delay(1000L)
            myBills.postValue(getMyBillList())
            state.loading = false
        }
    }

    override fun onItemSelected(pos: Int, bill: BillModel) {
        selectedBills.add(bill)
        state.totalBillAmount =
            state.totalBillAmount.plus(bill.amount?.toDouble() ?: 0.0)
        setButtonText()
        state.valid.set(true)
        adapter.setItemAt(pos, bill)
    }

    override fun onItemUnselected(pos: Int, bill: BillModel) {
        adapter.setItemAt(pos, bill)
        selectedBills.remove(bill)
        state.totalBillAmount =
            state.totalBillAmount.minus(bill.amount?.toDouble() ?: 0.0)
        setButtonText()
        if (selectedBills.size == 0) {
            state.valid.set(false)
        }
    }

    override fun setButtonText() {
        state.buttonText.set(
            Translator.getString(
                context,
                Strings.screen_my_bills_btn_text_pay,
                SessionManager.getDefaultCurrency(),
                state.totalBillAmount.toString()
            )
        )
    }

    override fun onStop() {
        super.onStop()
        toolgleRightIconVisibility(false)
    }
}
