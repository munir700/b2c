package co.yap.modules.subaccounts.paysalary.future

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import co.yap.R
import co.yap.modules.others.helper.Constants
import co.yap.networking.customers.household.CustomerHHApi
import co.yap.networking.customers.household.CustomersHHRepository
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.DateUtils.FORMAT_DATE_MON_YEAR
import co.yap.yapcore.helpers.DateUtils.GMT
import co.yap.yapcore.helpers.DateUtils.SERVER_DATE_FORMAT
import co.yap.yapcore.helpers.DateUtils.dateToString
import co.yap.yapcore.helpers.DateUtils.datetoString
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.hideKeyboard
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
class FuturePaymentVM @Inject constructor(override val state: FuturePaymentState) :
    HiltBaseViewModel<IFuturePayment.State>(), IFuturePayment.ViewModel,IValidator {
    private val calendar = Calendar.getInstance()
    private val repository: CustomerHHApi = CustomersHHRepository
    override var fragmentManager: FragmentManager? = null
    override val clickEvent = SingleClickEvent()
    override var validator: Validator? = Validator(null)

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        calendar.add(Calendar.DATE, 1)
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let {
            state.subAccount.value = it.getParcelable(SubAccount::class.java.simpleName)
            state.futureTransaction?.value = it.getParcelable(SchedulePayment::class.simpleName)
            state.amount.value = state.futureTransaction?.value?.amount?.apply {
                state.isValid.value = true
            }
            state.futureTransaction?.value?.nextProcessingDate?.apply {
                DateUtils.stringToDate(this, SERVER_DATE_FORMAT)?.run {
                    calendar.time = this
                    state.date.value = dateToString(calendar.time, FORMAT_DATE_MON_YEAR)
                }
            }
        }
    }

    override fun datePicker(view: View) {
        view.hideKeyboard()
        val dpd =
            DatePickerDialog.newInstance({ _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                state.date.value = dateToString(calendar.time, FORMAT_DATE_MON_YEAR)
            }, calendar)
        val minDateCalendar = Calendar.getInstance()
        minDateCalendar.add(Calendar.DATE, 1)
        dpd.minDate = minDateCalendar
        dpd.version = DatePickerDialog.Version.VERSION_2
        fragmentManager?.let {
            dpd.accentColor = context.getColor(R.color.colorPrimary)
            dpd.show(it, "")
        }
    }

    override fun onAmountChange(
        amount: CharSequence, start: Int, before: Int,
        count: Int
    ) {
        if (amount.parseToDouble() > GetAccountBalanceLiveData.cardBalance.value?.availableBalance.parseToDouble()) {
            context.showTextUpdatedAbleSnackBar(
                msg = "Looks like it’s time to Top Up! Please top up your account to continue with this transaction. ${underline(
                    "Top Up here!"
                )}", clickListener = View.OnClickListener { })
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
                    clickEvent.postValue(GO_TO_CONFIRMATION)
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
                clickEvent.postValue(id)
            }
            else -> {
                val time = datetoString(calendar.time, SERVER_DATE_FORMAT, GMT)
                val request = SchedulePayment(
                    amount = state.amount.value,
                    nextProcessingDate = time,
                    isRecurring = false
                )
                state.futureTransaction?.value?.scheduledPaymentUuid?.let {
                    request.scheduledPaymentUuid = it
                    updateSchedulePayment(state.subAccount.value?.accountUuid, request)
                } ?: createSchedulePayment(state.subAccount.value?.accountUuid, request)
            }
        }
    }

    override fun cancelSchedulePayment(scheduledPaymentUuid: String?) {
        launch {
            when (val response = repository.cancelSchedulePayment(
                scheduledPaymentUuid
            )) {
                is RetroApiResponse.Success -> {
                    clickEvent.postValue(Constants.EVENT_GO_BACK)

                }
                is RetroApiResponse.Error -> {
                }
            }
        }
    }

    override fun updateSchedulePayment(
        scheduledPaymentUuid: String?,
        request: SchedulePayment
    ) {
        launch {
            when (val response = repository.updateSchedulePayment(
                scheduledPaymentUuid, request
            )) {
                is RetroApiResponse.Success -> {
                    clickEvent.postValue(GO_TO_CONFIRMATION)
                }
                is RetroApiResponse.Error -> {
                }
            }
        }
    }
}
