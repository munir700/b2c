package co.yap.billpayments.paybill

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.databinding.FragmentPayBillBinding
import co.yap.billpayments.paybill.base.PayBillMainBaseFragment
import co.yap.billpayments.paybill.enum.PaymentScheduleType
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetConfiguration
import co.yap.widgets.bottomsheet.CoreBottomSheet
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.interfaces.OnItemClickListener

class PayBillFragment : PayBillMainBaseFragment<IPayBill.ViewModel>(),
    IPayBill.View, CompoundButton.OnCheckedChangeListener {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_pay_bill
    override val viewModel: PayBillViewModel
        get() = ViewModelProviders.of(this).get(PayBillViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewBinding().swAutoPayment.setOnCheckedChangeListener(this)
        getViewBinding().swBillReminder.setOnCheckedChangeListener(this)
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.cDay -> {
                viewModel.state.autoPaymentScheduleTypeWeek.set(false)
                viewModel.state.autoPaymentScheduleTypeMonth.set(false)
                viewModel.state.autoPaymentScheduleType.set(PaymentScheduleType.DAY.name)
            }
            R.id.cWeek -> {
                viewModel.state.autoPaymentScheduleTypeWeek.set(true)
                viewModel.state.autoPaymentScheduleTypeMonth.set(false)
                viewModel.state.autoPaymentScheduleType.set(PaymentScheduleType.WEEK.name)
            }
            R.id.cMonth -> {
                viewModel.state.autoPaymentScheduleTypeWeek.set(false)
                viewModel.state.autoPaymentScheduleTypeMonth.set(true)
                viewModel.state.autoPaymentScheduleType.set(PaymentScheduleType.MONTH.name)
            }
            R.id.tvDropDownWeekDays -> {
                this.childFragmentManager.let { fManager ->
                    val coreBottomSheet = CoreBottomSheet(
                        object :
                            OnItemClickListener {
                            override fun onItemClick(view: View, data: Any, pos: Int) {
                                viewModel.state.selectedWeekDay.set(weekDaysList[pos])
                            }
                        },
                        bottomSheetItems = composeWeekDaysList(),
                        configuration = BottomSheetConfiguration(
                            heading = getString(Strings.screen_pay_bill_select_week_day_text),
                            showSearch = false,
                            showHeaderSeparator = false
                        ),
                        viewType = Constants.VIEW_ITEM_WITHOUT_SEPARATOR
                    )
                    coreBottomSheet.show(fManager, "")
                }
            }
            R.id.btnPay -> {
                showToast("btnPay clicked")
            }
        }
    }

    override fun composeWeekDaysList(): MutableList<CoreBottomSheetData> {
        val list: MutableList<CoreBottomSheetData> = arrayListOf()
        weekDaysList.forEach { weekDay ->
            list.add(
                CoreBottomSheetData(
                    content = weekDay,
                    subTitle = weekDay,
                    sheetImage = null
                )
            )
        }
        return list
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

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}