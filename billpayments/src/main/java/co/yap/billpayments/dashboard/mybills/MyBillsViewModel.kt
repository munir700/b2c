package co.yap.billpayments.dashboard.mybills

import android.app.Application
import android.view.View
import co.yap.billpayments.R
import co.yap.billpayments.base.BillDashboardBaseViewModel
import co.yap.billpayments.dashboard.mybills.adapter.MyBillsAdapter
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.networking.customers.responsedtos.billpayment.BillStatus
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.interfaces.OnItemClickListener

class MyBillsViewModel(application: Application) :
    BillDashboardBaseViewModel<IMyBills.State>(application),
    IMyBills.ViewModel {
    override val state: IMyBills.State =
        MyBillsState()
    override var adapter: MyBillsAdapter =
        MyBillsAdapter(
            mutableListOf()
        )
    override var lastSelectionSorting: Int = -1
    override fun onResume() {
        super.onResume()
        setToolBarTitle(Translator.getString(context, Strings.screen_my_bills_toolbar_text_title))
        toggleRightIconVisibility(true)
        context.getDrawable(R.drawable.ic_add_sign)?.let { setRightIconDrawable(it) }
        parentViewModel?.billsResponse?.value?.toMutableList()?.let {
            if (it.size > 1) toggleSortIconVisibility(true) else toggleSortIconVisibility(false)
        }
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

    override fun setScreenTitle() {
        if (parentViewModel?.billsResponse?.value?.size == 1) {
            state.isBillsAvailable.set(true)
            state.screenTitle.set(
                Translator.getString(
                    context,
                    Strings.screen_my_bills_text_title_you_have_one_bill_registered
                )
            )
        } else if (2 <= parentViewModel?.billsResponse?.value?.size ?: 0) {
            state.isBillsAvailable.set(true)
            state.screenTitle.set(
                Translator.getString(
                    context,
                    Strings.screen_my_bills_text_title_you_have_n_bills_registered,
                    parentViewModel?.billsResponse?.value?.size.toString()
                )
            )
        } else {
            state.screenTitle.set("")
            state.isBillsAvailable.set(false)
        }
    }

    override fun setBillList() {
        setScreenTitle()
        parentViewModel?.billsResponse?.value?.toMutableList()?.let {
            if (it.size > 1) toggleSortIconVisibility(true) else toggleSortIconVisibility(false)
            adapter.setList(it)
        }
    }

    override val onBottomSheetItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            state.loading = true
            lastSelectionSorting = pos
            when (pos) {
                sortByDueDate -> {
                    adapter.getDataList().sortWith(
                        compareBy {
                            it.status?.let { it1 -> BillStatus.valueOf(it1) }
                        }
                    )
                }
                sortByRecentlyAdded -> {
                    val allPrepaidBills = adapter.getDataList()
                        .filter { s -> s.billerInfo?.skuInfos?.get(0)?.isPrepaid == true }
                    val allPostpaidBills = adapter.getDataList()
                        .filter { s -> s.billerInfo?.skuInfos?.get(0)?.isPrepaid == false }
                    val allPostpaidBillsnew = allPostpaidBills.sortedWith(compareByDescending {
                        it.createdAt?.let { it1 ->
                            DateUtils.stringToDate(
                                it1, DateUtils.SERVER_DATE_FULL_FORMAT
                            )
                        }
                    })
                    adapter.getDataList().clear()
                    adapter.setList(allPostpaidBillsnew + allPrepaidBills)

                }
                sortByAToZAscending -> {
                    adapter.getDataList().sortBy { billModel -> billModel.billerInfo?.billerName }
                }
                sortByZToADescending -> {
                    adapter.getDataList()
                        .sortByDescending { billModel -> billModel.billerInfo?.billerName }
                }
            }
            adapter.notifyDataSetChanged()
            state.loading = false
        }
    }
}
