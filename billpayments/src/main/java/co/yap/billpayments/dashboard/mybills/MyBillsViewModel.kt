package co.yap.billpayments.dashboard.mybills

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.R
import co.yap.billpayments.base.BillDashboardBaseViewModel
import co.yap.billpayments.dashboard.mybills.adapter.BillModel
import co.yap.billpayments.dashboard.mybills.adapter.MyBillsAdapter
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.networking.customers.responsedtos.billpayment.BillResponse
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.BillStatus
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.extentions.getJsonDataFromAsset
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay

class MyBillsViewModel(application: Application) :
    BillDashboardBaseViewModel<IMyBills.State>(application),
    IMyBills.ViewModel {
    override val state: IMyBills.State =
        MyBillsState()
    override var adapter: MyBillsAdapter =
        MyBillsAdapter(
            mutableListOf()
        )
    override var bills: MutableLiveData<MutableList<BillModel>> = MutableLiveData()
    override var billsList: MutableList<ViewBillModel> = mutableListOf()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var lastSelectionSorting: Int = -1
    override fun onResume() {
        super.onResume()
        setToolBarTitle(Translator.getString(context, Strings.screen_my_bills_toolbar_text_title))
        toggleSortIconVisibility(true)
        toggleRightIconVisibility(true)
        context.getDrawable(R.drawable.ic_add_sign)?.let { setRightIconDrawable(it) }
    }

    private fun getMyBillList(): BillResponse {
        val gson = GsonBuilder().create()
        return gson.fromJson<BillResponse>(
            context.getJsonDataFromAsset(
                "jsons/bill_list.json"
            ), object : TypeToken<BillResponse>() {}.type
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
            val myBillResponse = getMyBillList()
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
            billsList = myBillResponse.viewBillList.toMutableList()
            bills.postValue(adapterList)

            state.loading = false
        }
    }

    override fun setScreenTitle() {
        if (bills.value?.size == 1) {
            state.screenTitle.set(
                Translator.getString(
                    context,
                    Strings.screen_my_bills_text_title_you_have_one_bill_registered
                )
            )
        } else if (2 <= bills.value?.size ?: 0) {
            state.screenTitle.set(
                Translator.getString(
                    context,
                    Strings.screen_my_bills_text_title_you_have_n_bills_registered,
                    bills.value?.size.toString()
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
                    bills.value?.sortWith(
                        compareBy {
                            BillStatus.values()
                                .firstOrNull() { it1 -> it1.title.equals(it.billStatus) }?.ordinal
                        }
                    )
                }
                sortByRecentlyAdded -> {
                    bills.value?.sortWith(compareByDescending {
                        it.creationDate?.let { it1 ->
                            DateUtils.stringToDate(
                                it1, DateUtils.FORMATE_DATE_MONTH_YEAR
                            )
                        }
                    })
                }
                sortByAToZAscending -> {
                    bills.value?.sortBy { billModel -> billModel.billerName }
                }
                sortByZToADescending -> {
                    bills.value?.sortByDescending { billModel -> billModel.billerName }
                }
            }
            bills.value?.toMutableList()?.let { adapter.setList(it) }
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
        toggleSortIconVisibility(false)
    }
}
