package co.yap.billpayments.paybill

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.databinding.FragmentPayBillBinding
import co.yap.billpayments.payall.main.PayAllMainActivity
import co.yap.billpayments.paybill.base.PayBillMainBaseFragment
import co.yap.billpayments.paybill.enum.PaymentScheduleType
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetConfiguration
import co.yap.widgets.bottomsheet.CoreBottomSheet
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.interfaces.OnItemClickListener
import com.google.android.material.tabs.TabLayout

class PayBillFragment : PayBillMainBaseFragment<IPayBill.ViewModel>(),
    IPayBill.View, CompoundButton.OnCheckedChangeListener {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_pay_bill
    override val viewModel: PayBillViewModel
        get() = ViewModelProviders.of(this).get(PayBillViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.parentViewModel?.isPrepaid() == true)
            skipPayBillFragment()
        else
            setObservers()
    }

    private fun skipPayBillFragment() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.payBillFragment, true) // starting destination skipped
            .build()
        findNavController().navigate(
            R.id.action_payBillFragment_to_prepaidPayBillFragment,
            null,
            navOptions
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewBinding().swAutoPayment.setOnCheckedChangeListener(this)
        getViewBinding().swBillReminder.setOnCheckedChangeListener(this)
        initTabLayout()
        setEditTextWatcher()
        setValidation()
    }

    private fun setValidation() {
        viewModel.parentViewModel?.billModel?.value?.let {
            if (it.billerInfo?.skuInfos?.first()?.isPrepaid == false) {
                viewModel.setMinMaxLimitForPostPaid(it)
                getViewBinding().etAmount.setText(it.totalAmountDue ?: "0.00")
                if (it.billerInfo?.skuInfos?.first()?.isExcessPayment == false && it.billerInfo?.skuInfos?.first()?.isPartialPayment == false) {
                    getViewBinding().etAmount.isClickable = false
                    getViewBinding().etAmount.isEnabled = false
                    getViewBinding().etAmount.isFocusable = false
                    getViewBinding().etAmount.isFocusableInTouchMode = false
                }
            }
        }
    }

    private fun setEditTextWatcher() {
        getViewBinding().etAmount.afterTextChanged {
            if (it.isNotBlank()) {
                viewModel.state.amount = it
                if (viewModel.parentViewModel?.isPrepaid() == false)
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

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
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
                payBillNow()
            }
        }
    }

    private fun payBillNow() {
        viewModel.payBill(
            viewModel.getPayBillRequest(
                viewModel.parentViewModel?.billModel?.value,
                viewModel.state.amount
            )
        ) {
            viewModel.parentViewModel?.state?.paidAmount?.set(viewModel.state.amount)
            navigate(R.id.action_payBillFragment_to_payBillSuccessFragment)
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

    override fun getViewBinding(): FragmentPayBillBinding {
        return viewDataBinding as FragmentPayBillBinding
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}
