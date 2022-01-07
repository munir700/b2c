package co.yap.billpayments.billdetail.editbill

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.billdetail.base.BillDetailBaseFragment
import co.yap.billpayments.databinding.FragmentEditBillBinding
import co.yap.billpayments.utils.enums.PaymentScheduleType
import co.yap.billpayments.utils.enums.ReminderType
import co.yap.billpayments.utils.showAlertDialogAndExitApp
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetConfiguration
import co.yap.widgets.bottomsheet.CoreBottomSheet
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.customAlertDialog
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.interfaces.OnItemClickListener
import com.google.android.material.tabs.TabLayout

class EditBillFragment : BillDetailBaseFragment<IEditBill.ViewModel>(),
    IEditBill.View, CompoundButton.OnCheckedChangeListener {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_edit_bill
    var alertDialog: android.app.AlertDialog? = null

    override val viewModel: EditBillViewModel
        get() = ViewModelProviders.of(this).get(EditBillViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewBinding().swAutoPayment.setOnCheckedChangeListener(this)
        getViewBinding().swBillReminder.setOnCheckedChangeListener(this)
        getViewBinding().etNickName.afterTextChanged { viewModel.validation() }
        setObservers()
        initTabLayout()
        initReminderTabLayout()
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
                            totalDays = ReminderType.TwoWeeks().rdays
                        )
                    }
                }
            }

        })
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

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
        viewModel.editBillerError.observe(viewLifecycleOwner, Observer { errorCode ->
            errorCode?.let {
                requireContext().customAlertDialog(
                    topIconResId = R.drawable.ic_error_info_primary,
                    title = if (errorCode == viewModel.state.EVENT_WORNG_INPUT) getString(Strings.screen_bill_payment_add_bill_error_dialog_title)
                    else getString(Strings.screen_bill_payment_add_bill_service_error_dialog_title,viewModel.parentViewModel?.selectedBill?.billerInfo?.billerName ?: ""),
                    message = if (errorCode == viewModel.state.EVENT_WORNG_INPUT) getString(Strings.screen_bill_payment_add_bill_error_dialog_text)
                    else getString(Strings.screen_bill_payment_add_bill_service_error_dialog_text),
                    positiveButton = if (errorCode == viewModel.state.EVENT_WORNG_INPUT) getString(
                        Strings.screen_bill_payment_add_bill_error_dialog_p_button_text
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
        })
    }

    val clickObserver = Observer<Int> {
        when (it) {
            R.id.btnEditBill -> {
                editBillClick()
            }
            R.id.tvDeleteThisButton -> showPopUp()
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
            R.id.etNickName -> {
                viewModel.validation()
            }
        }
    }

    override fun showPopUp() {
        alertDialog = requireActivity().showAlertDialogAndExitApp(
            dialogTitle = getString(Strings.screen_edit_bill_dialog_title),
            message = getString(Strings.screen_edit_bill_dialog_description),
            leftButtonText = getString(Strings.common_button_yes),
            titleVisibility = true,
            isTwoButton = true,
            closeActivity = false,
            callback = {
                viewModel.deleteBill {
                    setIntentResult(isDeleted = true)
                }
            }
        )
    }

    private fun setIntentResult(isDeleted: Boolean = false, isUpdated: Boolean = false) {
        val intent = Intent()
        intent.putExtra(ExtraKeys.IS_DELETED.name, isDeleted)
        intent.putExtra(ExtraKeys.IS_UPDATED.name, isUpdated)
        requireActivity().setResult(Activity.RESULT_OK, intent)
        requireActivity().finish()
    }

    private fun editBillClick() {
        val request =
            viewModel.getEditBillerRequest()
        viewModel.editBill(request) {
            setIntentResult(isUpdated = true)
        }
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    override fun getViewBinding(): FragmentEditBillBinding {
        return viewDataBinding as FragmentEditBillBinding
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
        alertDialog?.dismiss()
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView?.id) {
            R.id.swAutoPayment -> {
                viewModel.state.isAutoPaymentOn.set(isChecked)
                viewModel.validation()
            }
            R.id.swBillReminder -> {
                viewModel.state.isBillReminderOn.set(isChecked)
                viewModel.validation()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val index = updateTabsReminderSelection(
            viewModel.parentViewModel?.selectedBill?.reminderFrequency
                ?: ReminderType.ThreeDays().rdays
        )
        getViewBinding().iBillReminder.tabLayout.getTabAt(index)?.select()
    }

    fun updateTabsReminderSelection(totalDays: Int): Int {
        return when (totalDays) {
            ReminderType.TwoWeeks().rdays -> threeWeeks
            ReminderType.OneWeek().rdays -> oneWeek
            ReminderType.ThreeDays().rdays -> threeDays
            else -> threeDays
        }
    }
}
