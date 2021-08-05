package co.yap.billpayments.paybill.prepaid

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.databinding.FragmentPrepaidPayBillBinding
import co.yap.billpayments.paybill.base.PayBillMainBaseFragment
import co.yap.billpayments.utils.enums.PaymentScheduleType
import co.yap.billpayments.utils.enums.ReminderType
import co.yap.networking.customers.responsedtos.billpayment.SkuCatalogs
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetConfiguration
import co.yap.widgets.bottomsheet.CoreBottomSheet
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.interfaces.OnItemClickListener
import com.google.android.material.tabs.TabLayout

class PrepaidPayBillFragment : PayBillMainBaseFragment<IPrepaidPayBill.ViewModel>(),
    IPrepaidPayBill.View, CompoundButton.OnCheckedChangeListener {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_prepaid_pay_bill
    override val viewModel: PrepaidPayBillViewModel
        get() = ViewModelProviders.of(this).get(PrepaidPayBillViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewBinding().swAutoPayment.setOnCheckedChangeListener(this)
        getViewBinding().swBillReminder.setOnCheckedChangeListener(this)
        initTabLayout()
        initReminderTabLayout()
        setEditTextWatcher()
        setValidation()
    }

    private fun setValidation() {
        if (viewModel.parentViewModel?.billModel?.value?.billerInfo?.skuInfos?.size == 1 &&
            viewModel.parentViewModel?.billModel?.value?.billerInfo?.skuInfos?.first()?.amount == "0.0"
        ) {
            viewModel.setMinMaxLimitForPrepaid(viewModel.parentViewModel?.billModel?.value as ViewBillModel)
            getViewBinding().etAmount.isClickable = true
            getViewBinding().etAmount.isEnabled = true
            getViewBinding().rvSkus.visibility = View.GONE
        } else {
            getViewBinding().etAmount.isClickable = false
            getViewBinding().etAmount.isEnabled = false
        }
    }

    private fun setEditTextWatcher() {
        getViewBinding().etAmount.afterTextChanged {
            if (it.isNotBlank()) {
                viewModel.state.amount = it
                if (getViewBinding().etAmount.isEnabled)
                    viewModel.checkOnTextChangeValidation(viewModel.state.amount.parseToDouble())
                else
                    viewModel.state.valid.set(true)
            } else {
                viewModel.state.valid.set(false)
                cancelAllSnackBar()
            }
        }
    }

    private fun initTabLayout() {
        getViewBinding().iAutoPayment.tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    day -> {
                        viewModel.updateAutoPaySelection(
                            isWeek = false,
                            isMonth = false,
                            paymentScheduleType = PaymentScheduleType.DAY
                        )
                    }
                    week -> {
                        viewModel.updateAutoPaySelection(
                            isWeek = true,
                            isMonth = false,
                            paymentScheduleType = PaymentScheduleType.WEEK
                        )
                    }
                    month -> {
                        viewModel.updateAutoPaySelection(
                            isWeek = false,
                            isMonth = true,
                            paymentScheduleType = PaymentScheduleType.MONTH
                        )
                    }
                }
            }

        })
    }

    private fun initReminderTabLayout() {
        getViewBinding().iBillReminder.tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    threeDays -> {
                        viewModel.updateReminderSelection(
                            isThreedays = true,
                            isOneWeek = false,
                            isThreeWeek = false,
                            totalDays = ReminderType.ThreeDays().rdays
                        )
                    }
                    oneWeek -> {
                        viewModel.updateReminderSelection(
                            isThreedays = false,
                            isOneWeek = true,
                            isThreeWeek = false,
                            totalDays = ReminderType.OneWeek().rdays
                        )
                    }
                    threeWeeks -> {
                        viewModel.updateReminderSelection(
                            isThreedays = false,
                            isOneWeek = false,
                            isThreeWeek = true,
                            totalDays = ReminderType.ThreeWeeks().rdays
                        )
                    }
                }
            }

        })
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.adapter.setItemListener(skuListener)
    }

    private val skuListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            getViewBinding().etAmount.setText((data as SkuCatalogs).amount.toFormattedCurrency())
            viewModel.selectedSku = data
        }
    }

    private val clickEvent = Observer<Int> {
        when (it) {
            R.id.tvDropDownWeekDays -> {
                openMonthDaysSheet(
                    title = getString(Strings.screen_pay_bill_select_week_day_text),
                    list = weekDaysList,
                    isDaySelection = true
                )
            }
            R.id.tvDropDownMonthDays -> {
                openMonthDaysSheet(
                    title = getString(Strings.screen_pay_bill_select_month_day_text),
                    list = monthDaysList,
                    isDaySelection = false
                )
            }
            R.id.btnPay -> {
                viewModel.payBillAndEditBiller(
                    payBillRequest = viewModel.getPayBillRequest(
                        viewModel.parentViewModel?.billModel?.value,
                        viewModel.state.amount
                    ), editBillerRequest = viewModel.getEditBillerRequest(
                        viewModel.parentViewModel?.billModel?.value
                    )
                ) {
                    viewModel.parentViewModel?.state?.paidAmount?.set(viewModel.state.amount)
                    navigate(R.id.action_prepaidPayBillFragment_to_payBillSuccessFragment)
                }
            }
        }
    }

    private fun openMonthDaysSheet(title: String, list: List<String>, isDaySelection: Boolean) {
        this.childFragmentManager.let { fManager ->
            val coreBottomSheet = CoreBottomSheet(
                object :
                    OnItemClickListener {
                    override fun onItemClick(view: View, data: Any, pos: Int) {
                        if (isDaySelection)
                            viewModel.state.selectedWeekDay.set(list[pos])
                        else
                            viewModel.state.selectedMonthDay.set(
                                list[pos]
                            )
                    }
                },
                bottomSheetItems = viewModel.composeWeekDaysList(list),
                configuration = BottomSheetConfiguration(
                    heading = title,
                    showSearch = false,
                    showHeaderSeparator = false
                ),
                viewType = Constants.VIEW_ITEM_WITHOUT_SEPARATOR
            )
            coreBottomSheet.show(fManager, "")
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        when (buttonView.id) {
            R.id.swAutoPayment -> {
                viewModel.state.isAutoPaymentOn.set(isChecked)
            }
            R.id.swBillReminder -> {
                viewModel.state.isBillReminderOn.set(isChecked)
            }
        }
    }

    override fun getViewBinding(): FragmentPrepaidPayBillBinding {
        return viewDataBinding as FragmentPrepaidPayBillBinding
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    override fun onResume() {
        super.onResume()
        val index = updateTabsReminderSelection(
            viewModel.parentViewModel?.billModel?.value?.reminderFrequency
                ?: ReminderType.ThreeDays().rdays
        )
        getViewBinding().iBillReminder.tabLayout.getTabAt(index)?.select()
    }

    fun updateTabsReminderSelection(totalDays: Int): Int {
        return when (totalDays) {
            ReminderType.ThreeWeeks().rdays -> threeWeeks
            ReminderType.OneWeek().rdays -> oneWeek
            ReminderType.ThreeDays().rdays -> threeDays
            else -> threeDays
        }
    }
}
