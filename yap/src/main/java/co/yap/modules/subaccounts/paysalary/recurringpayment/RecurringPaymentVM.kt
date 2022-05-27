package co.yap.modules.subaccounts.paysalary.recurringpayment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import co.yap.R
import co.yap.modules.others.helper.Constants.EVENT_GO_BACK
import co.yap.networking.customers.household.CustomerHHApi
import co.yap.networking.customers.household.CustomersHHRepository
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.DateUtils.FORMAT_DATE_SHORT_MON_YEAR
import co.yap.yapcore.helpers.DateUtils.SERVER_DATE_FORMAT
import co.yap.yapcore.helpers.DateUtils.dateToString
import co.yap.yapcore.helpers.DateUtils.stringToDate
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.helpers.livedata.GetAccountBalanceLiveData
import co.yap.yapcore.helpers.showTextUpdatedAbleSnackBar
import co.yap.yapcore.helpers.spannables.underline
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RecurringPaymentVM @Inject constructor(
    override var state: RecurringPaymentState
) :
    HiltBaseViewModel<IRecurringPayment.State>(), IRecurringPayment.ViewModel, IValidator {
    override var validator: Validator? = Validator(null)
    private val repository: CustomerHHApi = CustomersHHRepository
    private val calendar = Calendar.getInstance()
    override var fragmentManager: FragmentManager? = null
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        state.amount.value = state.recurringTransaction?.value?.amount?.apply {
            // validator?.validate()
        }
        state.recurringTransaction?.value?.nextProcessingDate?.apply {
            stringToDate(this, SERVER_DATE_FORMAT)?.run {
                calendar.time = this
                state.date.value = dateToString(calendar.time, FORMAT_DATE_SHORT_MON_YEAR)
            }
        }
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let {
            state.subAccount.value = it.getParcelable(SubAccount::class.java.simpleName)
            state.recurringTransaction?.value = it.getParcelable(SchedulePayment::class.simpleName)
        }
    }

    override fun datePicker() {
        val dpd =
            DatePickerDialog.newInstance({ view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                state.date.value = dateToString(calendar.time, FORMAT_DATE_SHORT_MON_YEAR)
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
            context.showTextUpdatedAbleSnackBar(
                msg = "Looks like it’s time to Top Up! Please top up your account to continue with this transaction. ${underline(
                    "Top Up here!"
                )}", marginTop = 0, clickListener = View.OnClickListener { })
        } else {
            cancelAllSnackBar()
        }
    }

    override fun createSchedulePayment(uuid: String?, schedulePayment: SchedulePayment?) {
        launch {
            when (val response = repository.createSchedulePayment(
                uuid,
                schedulePayment
            )) {
                is RetroApiResponse.Success -> {
                    clickEvent?.postValue(GO_TO_CONFIRMATION)
                }
                is RetroApiResponse.Error -> {
                }
            }
        }
    }

    override fun handlePressOnView(id: Int) {
        handleOnClick(id)
    }

    override fun handleOnClick(id: Int) {
        when (id) {
            R.id.tvCancel -> {
                clickEvent?.postValue(id)
            }
            else -> {
                val time =
                    DateUtils.datetoString(
                        calendar.time,
                        SERVER_DATE_FORMAT,
                        DateUtils.GMT
                    )

                state.schedulePayment.value = SchedulePayment(
                    amount = state.amount.value,
                    isRecurring = true,
                    recurringInterval = state.recurringInterval.value,
                    nextProcessingDate = time
                )
                state.recurringTransaction?.value?.scheduledPaymentUuid?.let {
                    state.schedulePayment.value?.scheduledPaymentUuid = it
                    updateSchedulePayment(state.subAccount.value?.accountUuid)
                } ?: createSchedulePayment(
                    state.subAccount.value?.accountUuid,
                    state.schedulePayment.value
                )
            }
        }
    }

    override fun cancelSchedulePayment(scheduledPaymentUuid: String?) {
        launch {
            when (val response = repository.cancelSchedulePayment(
                scheduledPaymentUuid
            )) {
                is RetroApiResponse.Success -> {
                    clickEvent?.postValue(EVENT_GO_BACK)
                }
                is RetroApiResponse.Error -> {
                }
            }
        }
    }

    override fun updateSchedulePayment(scheduledPaymentUuid: String?) {
        launch {
            when (val response = repository.updateSchedulePayment(
                scheduledPaymentUuid, state.schedulePayment.value
            )) {
                is RetroApiResponse.Success -> {
                    clickEvent?.postValue(GO_TO_CONFIRMATION)
                }
                is RetroApiResponse.Error -> {
                }
            }
        }
    }
}
