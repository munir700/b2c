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
import co.yap.billpayments.paybill.enum.PaymentScheduleType
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetConfiguration
import co.yap.widgets.bottomsheet.CoreBottomSheet
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.showAlertDialogAndExitApp
import co.yap.yapcore.interfaces.OnItemClickListener
import com.google.android.material.tabs.TabLayout

class EditBillFragment : BillDetailBaseFragment<IEditBill.ViewModel>(),
    IEditBill.View, CompoundButton.OnCheckedChangeListener {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_edit_bill

    override val viewModel: IEditBill.ViewModel
        get() = ViewModelProviders.of(this).get(EditBillViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewBinding().swAutoPayment.setOnCheckedChangeListener(this)
        getViewBinding().swBillReminder.setOnCheckedChangeListener(this)
        initTabLayout()
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
    }

    val clickObserver = Observer<Int> {
        when (it) {
            R.id.btnEditBill -> {
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
        }
    }

    override fun showPopUp() {
        requireActivity().showAlertDialogAndExitApp(
            dialogTitle = getString(Strings.screen_edit_bill_dialog_title),
            message = getString(Strings.screen_edit_bill_dialog_description),
            leftButtonText = getString(Strings.common_button_confirm),
            titleVisibility = true,
            isTwoButton = true,
            closeActivity = false,
            callback = {
                viewModel.deleteBill {
                    setIntentResult()
                }
            }
        )
    }

    private fun setIntentResult() {
        val intent = Intent()
        requireActivity().setResult(Activity.RESULT_OK, intent)
        requireActivity().finish()
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
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView?.id) {
            R.id.swAutoPayment -> {
                viewModel.state.isAutoPaymentOn.set(isChecked)
            }
            R.id.swBillReminder -> {
                viewModel.state.isBillReminderOn.set(isChecked)
            }
        }
    }
}
