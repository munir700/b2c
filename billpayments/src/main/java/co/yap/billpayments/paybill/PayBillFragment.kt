package co.yap.billpayments.paybill

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.databinding.FragmentPayBillBinding
import co.yap.billpayments.paybill.base.PayBillMainBaseFragment
import co.yap.billpayments.utils.enums.PaymentScheduleType
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetConfiguration
import co.yap.widgets.bottomsheet.CoreBottomSheet
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.FeatureSet
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.customAlertDialog
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.helpers.extentions.showBlockedFeatureAlert
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.FeatureProvisioning
import com.google.android.material.tabs.TabLayout

class PayBillFragment : PayBillMainBaseFragment<FragmentPayBillBinding, IPayBill.ViewModel>(),
    IPayBill.View, CompoundButton.OnCheckedChangeListener {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_pay_bill
    override val viewModel: PayBillViewModel by viewModels()

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
        viewDataBinding.swAutoPayment.setOnCheckedChangeListener(this)
        viewDataBinding.swBillReminder.setOnCheckedChangeListener(this)
        viewModel.editBillerError.observe(viewLifecycleOwner, editBillerError)
        initTabLayout()
        setEditTextWatcher()
        setValidation()

    }

    private fun setValidation() {
        viewModel.parentViewModel?.billModel?.value?.let {
            if (it.billerInfo?.skuInfos?.first()?.isPrepaid == false) {
                viewModel.setMinMaxLimitForPostPaid(it)
                with(viewDataBinding) {
                    etAmount.setText(it.totalAmountDue ?: "0.00")
                    if (it.billerInfo?.skuInfos?.first()?.isExcessPayment == false && it.billerInfo?.skuInfos?.first()?.isPartialPayment == false) {
                        viewDataBinding.etAmount.apply {
                            isClickable = false
                            isEnabled = false
                            isFocusable = false
                            isFocusableInTouchMode = false
                        }
                    }
                }

            }
        }
    }

    private fun setErrorBorder() {
        viewDataBinding.etAmount.background =
            this.resources.getDrawable(
                if (viewModel.state.isError.get()) co.yap.yapcore.R.drawable.bg_funds_error else co.yap.yapcore.R.drawable.bg_funds,
                null
            )
    }

    private fun setEditTextWatcher() {
        viewDataBinding.etAmount.afterTextChanged {
            if (it.isNotBlank()) {
                viewModel.state.amount = it
                if (viewModel.parentViewModel?.isPrepaid() == false) {
                    viewModel.checkOnTextChangeValidation(viewModel.state.amount.parseToDouble())
                } else
                    viewModel.state.valid.set(true)
            } else {
                viewModel.state.valid.set(false)
                viewModel.state.isError.set(false)
                cancelAllSnackBar()
            }
            setErrorBorder()
        }
    }

    private fun initTabLayout() {
        viewDataBinding.iAutoPayment.tabLayout.addOnTabSelectedListener(object :
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

    private val editBillerError = Observer<Int?> { errorCode ->
        errorCode?.let {
            requireContext().customAlertDialog(
                topIconResId = R.drawable.ic_error_info_primary,
                title = if (errorCode == viewModel.state.EVENT_WORNG_INPUT) getString(Strings.screen_bill_payment_add_bill_error_dialog_title)
                else getString(
                    Strings.screen_bill_payment_add_bill_service_error_dialog_title,
                    viewModel.parentViewModel?.billModel?.value?.billerInfo?.billerName ?: ""
                ),
                message = if (errorCode == viewModel.state.EVENT_WORNG_INPUT) getString(Strings.screen_bill_payment_add_bill_error_dialog_text)
                else getString(Strings.screen_bill_payment_add_bill_service_error_dialog_text),
                positiveButton = if (errorCode == viewModel.state.EVENT_WORNG_INPUT) getString(
                    Strings.common_text_edit_now
                )
                else null,
                negativeButton = if (errorCode == viewModel.state.EVENT_WORNG_INPUT) getString(
                    Strings.screen_bill_payment_add_bill_error_dialog_n_button_text
                )
                else getString(Strings.screen_bill_payment_add_bill_service_error_dialog_button_text),
                cancelable = false,
                negativeCallback = {
                    if (errorCode == viewModel.state.EVENT_WORNG_INPUT) navigateBack()
                }
            )
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
                if (FeatureProvisioning.getFeatureProvisioning(FeatureSet.PAY_BILL_PAYMENT)) {
                    showBlockedFeatureAlert(requireActivity(), FeatureSet.PAY_BILL_PAYMENT)
                } else {
                    viewModel.payBillAndEditBiller(
                        payBillRequest = viewModel.getPayBillRequest(
                            viewModel.parentViewModel?.billModel?.value,
                            viewModel.state.amount
                        ), editBillerRequest = viewModel.getEditBillerRequest(
                            viewModel.parentViewModel?.billModel?.value
                        )
                    ) {
                        viewModel.parentViewModel?.state?.paidAmount?.set(viewModel.state.amount)
                        navigate(R.id.action_payBillFragment_to_payBillSuccessFragment)
                    }
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

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
        viewModel.editBillerError.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}
