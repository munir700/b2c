package co.yap.billpayments.mybills

import android.app.Application
import android.view.View
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
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager
import kotlinx.coroutines.delay

class MyBillsViewModel(application: Application) :
    PayBillBaseViewModel<IMyBills.State>(application), IMyBills.ViewModel {
    override val state: IMyBills.State = MyBillsState()
    override var adapter: MyBillsAdapter = MyBillsAdapter(mutableListOf())
    override var myBills: MutableLiveData<MutableList<BillModel>> = MutableLiveData()
    override var selectedBills: MutableList<BillModel> = mutableListOf()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var lastSelectionSorting: Int = -1
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
                name = "Sarpinos",
                description = "Burj Telecom Residences",
                currency = "USD",
                creationDate = "2021-06-12T06:53:35",
                amount = "600",
                billStatus = BillStatus.PAID.title
            ),
            BillModel(
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                name = "KFC",
                description = "Burj Telecom Residences",
                currency = "USD",
                creationDate = "2021-06-12T06:53:35",
                amount = "600",
                billStatus = BillStatus.PAID.title
            ),
            BillModel(
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                name = "Texas Steak house",
                description = "Burj ul Dubai",
                creationDate = "2021-01-12T06:53:35",
                currency = "AED",
                amount = "100",
                billStatus = BillStatus.OVERDUE.title
            ),
            BillModel(
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                name = "PF changs",
                description = "Burj Telecom Residences",
                currency = "AED",
                creationDate = "2021-02-12T06:53:35",
                amount = "200",
                billStatus = BillStatus.OVERDUE.title
            ),
            BillModel(
                logoUrl = "",
                name = "Salt and Paper",
                description = "Burj ul khalifa",
                currency = "AED",
                creationDate = "2021-03-12T06:53:35",
                amount = "300",
                billStatus = BillStatus.BILL_DUE.title
            ),
            BillModel(
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                name = "Mcdonalds",
                description = "Burj Telecom Residences",
                currency = "AED",
                creationDate = "2020-04-12T06:53:35",
                amount = "400",
                billStatus = BillStatus.OVERDUE.title
            ),
            BillModel(
                logoUrl = "",
                name = "Daily Deli",
                description = "Burj Telecom Residences",
                currency = "USD",
                creationDate = "2022-05-12T06:53:35",
                amount = "500",
                billStatus = BillStatus.BILL_DUE.title
            ),
            BillModel(
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                name = "Broadway",
                description = "Burj Telecom Residences",
                currency = "USD",
                creationDate = "2021-06-12T06:53:35",
                amount = "600",
                billStatus = BillStatus.PAID.title
            ),
            BillModel(
                logoUrl = "",
                name = "Nandos",
                description = "Burj Telecom Residences",
                currency = "USD",
                creationDate = "2021-07-20T06:53:35",
                amount = "700",
                billStatus = BillStatus.OVERDUE.title
            )
        )
    }

    override fun getFiltersList(): MutableList<CoreBottomSheetData> {
        val sortingOptionList = mutableListOf(
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

        if (lastSelectionSorting != -1) {
            sortingOptionList[lastSelectionSorting].isSelected = true
        }

        return sortingOptionList
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
                it.creationDate = DateUtils.reformatStringDate(
                    it.creationDate.toString(),
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

    override val onBottomSheetItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            state.loading = true
            lastSelectionSorting = pos
            when (pos) {
                sortByDueDate -> {
                    myBills.value?.sortWith(
                        compareBy {
                            BillStatus.values()
                                .firstOrNull() { it1 -> it1.title.equals(it.billStatus) }?.ordinal
                        }
                    )
                }
                sortByRecentlyAdded -> {
                    myBills.value?.sortWith(compareBy {
                        it.creationDate?.let { it1 ->
                            DateUtils.stringToDate(
                                it1, DateUtils.FORMATE_DATE_MONTH_YEAR
                            )
                        }
                    })
                }
                sortByAToZAscending -> {
                    myBills.value?.sortBy { billModel -> billModel.name }
                }
                sortByZToADescending -> {
                    myBills.value?.sortByDescending { billModel -> billModel.name }
                }
            }
            myBills.value?.toMutableList()?.let { adapter.setList(it) }
            state.loading = false
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
