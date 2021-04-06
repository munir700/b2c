package co.yap.billpayments.mybills

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.billpayments.mybills.adapter.MyBillsAdapter
import co.yap.networking.customers.responsedtos.billpayment.BillModel
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.widgets.bottomsheet.CoreBottomSheetData
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.BillStatus
import co.yap.yapcore.helpers.DateUtils
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
        toolgleRightFirstIconVisibility(true)
        toolgleRightSecondIconVisibility(true)
        context.getDrawable(R.drawable.ic_sort)?.let { setRightFirstIconDrawable(it) }
        context.getDrawable(R.drawable.ic_add_sign)?.let { setRightSecondIconDrawable(it) }
    }

    private fun getMyBillList(): MutableList<BillModel> {
        return mutableListOf(
            BillModel(
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                name = "Etsilat",
                description = "Burj ul Dubai",
                billDueDate = "2021-02-12T06:53:35",
                currency = "AED",
                amount = "100",
                billStatus = BillStatus.OVERDUE.title
            ),
            BillModel(
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                name = "Etsilat",
                description = "Burj Telecom Residences",
                currency = "AED",
                billDueDate = "2021-05-12T06:53:35",
                amount = "100",
                billStatus = BillStatus.OVERDUE.title
            ),
            BillModel(
                logoUrl = "",
                name = "Salik",
                description = "Burj ul khalifa",
                currency = "AED",
                billDueDate = "2021-09-12T06:53:35",
                amount = "100",
                billStatus = BillStatus.BILL_DUE.title
            ),
            BillModel(
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                name = "Etsilat",
                description = "Burj Telecom Residences",
                currency = "AED",
                billDueDate = "2020-08-12T06:53:35",
                amount = "100",
                billStatus = BillStatus.OVERDUE.title
            ),
            BillModel(
                logoUrl = "",
                name = "Etsilat",
                description = "Burj Telecom Residences",
                currency = "USD",
                billDueDate = "2022-08-12T06:53:35",
                amount = "100",
                billStatus = BillStatus.BILL_DUE.title
            ),
            BillModel(
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                name = "Etsilat",
                description = "Burj Telecom Residences",
                currency = "USD",
                billDueDate = "2021-08-12T06:53:35",
                amount = "100",
                billStatus = BillStatus.PAID.title
            ),
            BillModel(
                logoUrl = "",
                name = "Etsilat",
                description = "Burj Telecom Residences",
                currency = "USD",
                billDueDate = "2021-18-12T06:53:35",
                amount = "100",
                billStatus = BillStatus.OVERDUE.title
            )
        )
    }

    override fun getFiltersList(): MutableList<CoreBottomSheetData> {
        return mutableListOf(
            CoreBottomSheetData(
                subTitle = "Due date",
                isSelected = false
            ),
            CoreBottomSheetData(
                subTitle = "Recently added",
                isSelected = false
            ),
            CoreBottomSheetData(
                subTitle = "A-Z ascending",
                isSelected = false
            ),
            CoreBottomSheetData(
                subTitle = "Z-A descending",
                isSelected = false
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
            val list = getMyBillList()
            list.forEach {
                it.billDueDate = DateUtils.reformatStringDate(
                    it.billDueDate.toString(),
                    DateUtils.SERVER_DATE_FULL_FORMAT,
                    DateUtils.FORMATE_DATE_MONTH_YEAR
                )
            }
            myBills.postValue(list)

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
        if (selectedBills.size == 1) {
            state.screenTitle.set(
                Translator.getString(
                    context,
                    Strings.screen_my_bills_text_title_on_selection
                )
            )
        }
    }

    override fun onItemUnselected(pos: Int, bill: BillModel) {
        adapter.setItemAt(pos, bill)
        selectedBills.remove(bill)
        state.totalBillAmount =
            state.totalBillAmount.minus(bill.amount?.toDouble() ?: 0.0)
        setButtonText()
        if (selectedBills.size == 0) {
            state.valid.set(false)
            getScreenTitle()
        }
    }

    override fun getScreenTitle() {
        if (myBills.value?.size == 1) {
            state.screenTitle.set(
                Translator.getString(
                    context,
                    Strings.screen_my_bills_text_title_you_have_one_bill_registered
                )
            )
        } else if (2 <= myBills.value?.size ?: 0) {
            state.screenTitle.set(
                Translator.getString(
                    context,
                    Strings.screen_my_bills_text_title_you_have_n_bills_registered,
                    myBills.value?.size.toString()
                )
            )
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
        toolgleRightFirstIconVisibility(false)
        toolgleRightSecondIconVisibility(false)
    }
}
