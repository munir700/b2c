package co.yap.modules.subaccounts.paysalary.recurringpayment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import co.yap.R
import co.yap.networking.customers.household.CustomerHHApi
import co.yap.networking.customers.household.CustomersHHRepository
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.DateUtils.FORMAT_DATE_MON_YEAR
import co.yap.yapcore.helpers.DateUtils.dateToString
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.helpers.livedata.GetAccountBalanceLiveData
import co.yap.yapcore.helpers.showTextUpdatedAbleSnackBar
import co.yap.yapcore.helpers.spannables.underline
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*
import javax.inject.Inject

class RecurringPaymentVM @Inject constructor(
    override var state: IRecurringPayment.State
) :
    DaggerBaseViewModel<IRecurringPayment.State>(), IRecurringPayment.ViewModel {
    private val repository: CustomerHHApi = CustomersHHRepository
    private val calendar = Calendar.getInstance()
    override var fragmentManager: FragmentManager? = null
    override val clickEvent = SingleClickEvent()
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let {
            state.subAccount.value = it.getParcelable(SubAccount::class.simpleName)
            val payment: SchedulePayment? = it.getParcelable(SchedulePayment::class.simpleName)
            state.amount.value = payment?.amount?.apply {
                state.isValid.value = true
            }
        }
    }

    override fun datePicker() {
        val dpd =
            DatePickerDialog.newInstance({ view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                state.date.set(dateToString(calendar.time, FORMAT_DATE_MON_YEAR))
            }, calendar)
        dpd.minDate = Calendar.getInstance()
        dpd.version = DatePickerDialog.Version.VERSION_2
        fragmentManager?.let {
            dpd.accentColor = context.getColor(R.color.colorPrimary)
            dpd.show(it, "")
        }
    }

    override fun onCheckedChanged(text: String, isChecked: Boolean) {
        state.recurringInterval.value = text
    }

    override fun onAmountChange(
        amount: CharSequence, start: Int, before: Int,
        count: Int
    ) {
        if (amount.parseToDouble() > GetAccountBalanceLiveData.cardBalance.value?.availableBalance.parseToDouble()) {
            state.isValid.value = false
            context.showTextUpdatedAbleSnackBar(
                msg = "Looks like it’s time to Top Up! Please top up your account to continue with this transaction. ${underline(
                    "Top Up here!"
                )}", marginTop = 0, clickListener = View.OnClickListener { })
        } else {
            cancelAllSnackBar()
            state.isValid.value = true
        }
    }

    override fun createSchedulePayment(uuid: String?, schedulePayment: SchedulePayment?) {
        launch {
            when (val response = repository.createSchedulePayment(
                uuid,
                schedulePayment
            )) {
                is RetroApiResponse.Success -> {
                    clickEvent.postValue(GO_TO_CONFIRMATION)
                }
                is RetroApiResponse.Error -> {
                }
            }
        }
    }

    override fun handlePressOnClick(id: Int) {
        val time = DateUtils.datetoString(calendar.time, DateUtils.FORMAT_LONG_INPUT, DateUtils.GMT)
        state.schedulePayment.value = SchedulePayment(
            amount = state.amount.value,
            isRecurring = true,
            recurringInterval = state.recurringInterval.value,
            nextProcessingDate = time
        )
        createSchedulePayment(state.subAccount.value?.accountUuid, state.schedulePayment.value)
    }
}
